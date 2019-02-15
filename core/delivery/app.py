#!/usr/bin/env python
# -*- coding: utf-8 -*-
import configparser
import datetime
import json
import os
import queue
import signal
import sys
import threading
import uuid
from random import randint, choice

from klein import Klein
from time import sleep
from twisted.web.static import File

from producerconsumer.kafka_producer_worker import kafka_producer_worker
from producerconsumer.kafka_consumer_worker import kafka_consumer_worker
from message.make_kafka_message import make_kafka_message

__product__ = "Core Delivery"
__author__ = "Nikita ROUSSEAU"
__copyright__ = "Copyright 2018, Polytech Nice Sophia"
__credits__ = ["Nikita Rousseau"]
__license__ = "MIT"
__version__ = "2.0"
__maintainer__ = "Nikita ROUSSEAU"
__email__ = "nikita.rousseau@etu.unice.fr"
__status__ = "development"

# Web Application
app = Klein()

# GLOBAL SERVER SETTINGS
# GLOBAL APPLICATION CONFIGURATION
env = 'development'
app_config = {}
bootstrap_servers = []
topics = []
# GLOBAL THREAD REGISTRY
threads = []
threads_mq = {}
# CLEAN EXIT EVENT
t_stop_event = threading.Event()
# Routes and deliveries state
routes = {}
deliveries = {}

cities = {
    'world': {
        0: 'Nice'
    },
    'europe-west1-b': {
        0: 'Marseille',
        1: 'Antibes',
        2: 'Toulon',
        3: 'Aix-en-provence',
        4: 'Nice'
    },
    'europe-west2-c': {
        0: 'London',
        1: 'Bristol',
        2: 'Manchester',
        3: 'Liverpool'
    }
}


def __sigint_handler(sig, frame):
    """
    Catch CTR+C / KILL signals
    Do housekeeping before leaving
    """
    t_stop_event.set()  # Set stop flag to true for all launched threads
    sleep(1)
    os.kill(os.getpid(), signal.SIGTERM)


signal.signal(signal.SIGINT, __sigint_handler)
signal.signal(signal.SIGTERM, __sigint_handler)


def __load_config():
    """
    Parse database configuration file
    """
    config_file = os.path.join(os.path.dirname(os.path.realpath(__file__)), "config.ini")
    if not os.path.exists(config_file):
        raise FileNotFoundError(config_file)
    app_config = configparser.ConfigParser()
    app_config.read(config_file)
    return app_config


########################################################################################################################
# COMMON ROUTES
########################################################################################################################


# We are serving a static file, branch=True is required
@app.route('/delivery/api',
           methods=['GET'],
           branch=True)
def root_route(request):
    # API DOCUMENTATION ROOT
    return File('./swagger/index.html')


# Health check
@app.route('/delivery/healthz')
def status_route(request):
    return '200 OK'


########################################################################################################################
# CORE DELIVERY SERVICE ROUTES
########################################################################################################################

@app.route("/delivery/route",
           methods=['POST'])
def post_route(request):
    """
    Create a new route
    :return:
    """
    uuid_route=str(uuid.uuid4())
    # Build message
    message, request_id = make_kafka_message(
        action='ROUTE_CREATED',
        message={
            'route_uuid': uuid_route,
            'time': str(datetime.datetime.now().replace(microsecond=0).isoformat()),
            'initial_city': cities[region][randint(0, len(cities[region])-1)],
            'end_city': cities[region][randint(0, len(cities[region])-1)]
        }
    )

    # Send
    threads_mq['route'].put(message)
    print("route created")
	# Store the route
    routes[uuid_route] = "created"
    print("Length : " + str(len(routes)))
    # Response with callback url
    return json.dumps(dict(message),)


@app.route("/delivery/route",
           methods=['DELETE'])
def post_route_cancellation(request):
    """
    Cancel a route
    :return:
    """
    created_routes = {k:v for k,v in routes.items() if  v == "created"}
    if len(created_routes) > 0 :
        to_cancel = choice(list(created_routes))
        routes.pop(to_cancel)
        # Build message
        message, request_id = make_kafka_message(
           action='ROUTE_CANCELED',
           message={
               'route_uuid': to_cancel,
               'time': str(datetime.datetime.now().replace(microsecond=0).isoformat())
           }
        )

        # Send
        threads_mq['route'].put(message)

        # Response with callback url
        return json.dumps(dict(message),)
    return "oupsy"

@app.route("/delivery",
           methods=['POST'])
def post_delivery_route(request):
    """
    Create a new delivery
    :return:
    """
    created_routes = {k:v for k,v in routes.items() if  v == "created"}
    # Build message
    if len(created_routes) > 0 :
        to_init = choice(list(created_routes))
        delivery_id = str(uuid.uuid4())
        routes[to_init] = "initiated"
        deliveries[to_init] = delivery_id
        message, request_id = make_kafka_message(
            action='DELIVERY_INITIATED',
            message={
                'delivery_uuid': delivery_id,
                'city': cities[region][randint(0, len(cities[region])-1)],
                'time': str(datetime.datetime.now().replace(microsecond=0).isoformat()),
                'route_uuid': to_init,
            }
        )

        # Send
        threads_mq['delivery'].put(message)

        # Response with callback url
        return json.dumps(dict(message),)
    return "oupsy"


@app.route("/delivery/checkpoint",
           methods=['POST'])
def post_delivery_checkpoint_route(request):
    """
    Notify that the delivery has reached a checkpoint
    :return:
    """
    # Build message
    is_final_destination = False
    if randint(0, 1) > 0:
        is_final_destination = True
    message, request_id = make_kafka_message(
        action='DELIVERY_CHECKPOINT',
        message={
            'delivery_id': randint(1, 9999),
            'city': cities[region][randint(0, len(cities[region])-1)],
            'time': str(datetime.datetime.now().replace(microsecond=0).isoformat()),
            'isFinalDestination': is_final_destination
        }
    )

    # Send
    threads_mq['delivery'].put(message)

    # Response with callback url
    return json.dumps(dict(message),)


@app.route("/delivery/issue",
           methods=['POST'])
def post_delivery_issue(request):
    """
    Notify that a specific delivery has had an issue.
    :return:
    """

    initiated_routes = {k:v for k,v in routes.items() if  v == "initiated"}
    if len(initiated_routes) > 0 :
        issue_encountered = choice(list(initiated_routes))
        routes[issue_encountered] = "has_issue"
        # Build message
        message, request_id = make_kafka_message(
            action='DELIVERY_ISSUE',
            message={
                'delivery_uuid': deliveries[issue_encountered],
                'issue_type': "DELIVERY_MISSING" if randint(1, 2) > 1 else "DAMAGED_DELIVERY",
                'time': str(datetime.datetime.now().replace(microsecond=0).isoformat())
            }
        )
    
        # Send
        threads_mq['delivery'].put(message)
    
        # Response with callback url
        return json.dumps(dict(message),)
    return "oupsy"

########################################################################################################################
# END: ROUTES
########################################################################################################################


if __name__ == '__main__':
    # ENVIRONMENT
    if len(sys.argv) > 1 and str(sys.argv[1]) == 'production':
        env = 'production'
    else:
        env = 'development'

    # REGION
    region = "world"
    if len(sys.argv) > 2:
        region = str(sys.argv[2])

    # CONFIGURATION
    app_config_raw = __load_config()
    app_config = app_config_raw[env]

    host = app_config['host']
    port = app_config['port']

    # Bootstrap servers
    if ',' in str(app_config['bootstrap_servers']):
        bootstrap_servers = list(filter(None, str(app_config['bootstrap_servers']).split(',')))
    else:
        bootstrap_servers.append(str(app_config['bootstrap_servers']))

    # Topics
    if ',' in str(app_config['topics']):
        topics = list(filter(None, str(app_config['topics']).split(',')))
    else:
        topics.append(str(app_config['topics']))

    ###########################################################

    for topic in topics:
        # I/O message queue
        mq = queue.Queue()
        threads_mq[topic] = mq

        # Producer Worker
        t_producer_worker = threading.Thread(
            name='kafka_' + topic + '_producer_worker',
            daemon=True,
            target=kafka_producer_worker,
            args=(t_stop_event, bootstrap_servers, topic, mq,)
        )
        threads.append(t_producer_worker)

    # heartbeat consumer
    t_kafka_hb_consumer_worker = threading.Thread(
        name='kafka_heartbeat_consumer_worker',
        daemon=True,
        target=kafka_consumer_worker,
        args=(t_stop_event, bootstrap_servers, 'heartbeat', region, __product__, threads_mq)
    )
    threads.append(t_kafka_hb_consumer_worker)

    ###########################################################

    # Starting threads
    for t in threads:
        t.start()

    print('[' + region + '] ' + __product__ + ' version ' + __version__ + ' (' + env + ') is listening "' + host + ':' + port + '"')

    # Http server
    # log = open('app.log', 'a')
    # if env == 'production':
    #    log = open('/dev/null', 'a')
    # app.run(host, port, log)

    app.run(host, port)

    exit(0)
