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
from random import randint

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


cities = {
    0: 'Marseille',
    1: 'Antibes',
    2: 'Toulon',
    3: 'Aix-en-provence',
    4: 'Nice'
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
    config_file = os.path.join(os.path.dirname(
        os.path.realpath(__file__)), "config.ini")
    if not os.path.exists(config_file):
        raise FileNotFoundError(config_file)
    app_config = configparser.ConfigParser()
    app_config.read(config_file)
    return app_config


########################################################################################################################
# COMMON ROUTES
########################################################################################################################


# We are serving a static file, branch=True is required
@app.route('/', branch=True)
def root_route(request):
    # API DOCUMENTATION ROOT
    return File('./swagger')


@app.route('/status')
def status_route(request):
    return '200 OK'


########################################################################################################################
# CORE DELIVERY SERVICE ROUTES
########################################################################################################################

@app.route("/delivery",
           methods=['POST'])
def post_delivery_route(request):
    """
    Create a new delivery
    :return:
    """
    # Build message
    message, request_id = make_kafka_message(
        action='DELIVERY_INITIATED',
        message={
            'delivery_uuid': str(uuid.uuid4()),
            'city': cities[randint(0, len(cities)-1)],
            'time': str(datetime.datetime.now().replace(microsecond=0).isoformat())
        }
    )

    # Send
    threads_mq['delivery'].put(message)

    # Response with callback url
    return json.dumps(dict(message),)


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
            'city': cities[randint(0, len(cities)-1)],
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
    # Build message
    message, request_id = make_kafka_message(
        action='DELIVERY_ISSUE',
        message={
            'issue_type': "DELIVERY_MISSING" if randint(1, 2) > 1 else "DAMAGED_DELIVERY",
            'time': str(datetime.datetime.now().replace(microsecond=0).isoformat())
        }
    )

    # Send
    threads_mq['delivery'].put(message)

    # Response with callback url
    return json.dumps(dict(message),)


########################################################################################################################
# END: ROUTES
########################################################################################################################


if __name__ == '__main__':
    # ENVIRONMENT
    if len(sys.argv) > 1 and str(sys.argv[1]) == 'production':
        env = 'production'
    else:
        env = 'development'

    # CONFIGURATION
    app_config_raw = __load_config()
    app_config = app_config_raw[env]

    host = app_config['host']
    port = app_config['port']

    # Bootstrap servers
    if ',' in str(app_config['bootstrap_servers']):
        bootstrap_servers = list(
            filter(None, str(app_config['bootstrap_servers']).split(',')))
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
        args=(t_stop_event, bootstrap_servers,
              'heartbeat', __product__, threads_mq)
    )
    threads.append(t_kafka_hb_consumer_worker)

    ###########################################################

    # Starting threads
    for t in threads:
        t.start()

    print(__product__ + ' version ' + __version__ + ' (' + env +
          ') is listening on socket "' + host + ':' + port + '"')

    # Http server
    # log = open('app.log', 'a')
    # if env == 'production':
    #    log = open('/dev/null', 'a')
    # app.run(host, port, log)

    app.run(host, port)

    exit(0)
