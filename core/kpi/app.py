#!/usr/bin/env python
# -*- coding: utf-8 -*-
import configparser
import json
import os
import queue
import signal
import sys
import threading
from time import sleep

from klein import Klein
from twisted.web.static import File

from message.make_kafka_message import make_kafka_message
from producerconsumer.kafka_consumer_worker import kafka_consumer_worker
from producerconsumer.kafka_producer_worker import kafka_producer_worker

__product__ = "Core KPI"
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
@app.route('/kpi/api',
           methods=['GET'],
           branch=True)
def root_route(request):
    # API DOCUMENTATION ROOT
    return File('./swagger')


# Health check
@app.route('/kpi/healthz')
def status_route(request):
    return '200 OK'


########################################################################################################################
# CORE KPI SERVICE ROUTES
########################################################################################################################


@app.route("/kpi/city/top10",
           methods=['GET'])
def get_kpi_city_top10_route():
    """
    Ask the system the most active cities by initiated deliveries
    :return:
    """
    # Build message
    message, request_id = make_kafka_message(
        action='KPI_CITY_TOP10_BROADCAST',
        message={}
    )

    # Send
    threads_mq['kpi'].put(message)

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
