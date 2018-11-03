#!/usr/bin/env python
# -*- coding: utf-8 -*-
import configparser
import json
import logging
import os
import queue
import signal
import sys
import threading
from time import sleep

from flask import Flask, jsonify, render_template
from flask.logging import default_handler
from kafka import KafkaProducer

from message.factory import make_kafka_message

__product__ = "Core KPI"
__author__ = "Nikita ROUSSEAU"
__copyright__ = "Copyright 2018, Polytech Nice Sophia"
__credits__ = ["Nikita Rousseau"]
__license__ = "MIT"
__version__ = "1.0"
__maintainer__ = "Nikita ROUSSEAU"
__email__ = "nikita.rousseau@etu.unice.fr"
__status__ = "development"

# Flask Application
app = Flask(__name__)

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


def __sigint_handler(signal, frame):
    """
    Catch CTR+C / KILL signals
    Do housekeeping before leaving
    """
    logging.debug("SIGINT or SIGTERM catched")
    logging.debug("Raise t_stop_event")
    t_stop_event.set()  # Set stop flag to true for all launched threads
    logging.info("Stopping daemons...")
    sleep(1)
    sys.exit(1)


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


@app.route('/')
def root_route():
    # API DOCUMENTATION ROOT
    return render_template('swagger/index.html')


@app.route('/status')
def status_route():
    return jsonify(
        {'status': 'online'}
    ), 200


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
    return jsonify({
        "message": message
    }), 200


########################################################################################################################
# END: ROUTES
########################################################################################################################


def http_server_worker():
    # Http server
    app.run("0.0.0.0", port=5000, use_reloader=False, threaded=True)
    return


def kafka_producer_worker(topic: str, mq: queue.Queue):
    """
    Kafka Generic Message Producer
    as thread worker
    Get messages from a shared mq queue.Queue
    :param topic: str
    :param mq: queue.Queue
    :return:
    """
    # Client
    producer = KafkaProducer(bootstrap_servers=bootstrap_servers,
                             value_serializer=lambda item: json.dumps(item).encode('utf-8'))

    while not t_stop_event.is_set():
        # 10ms
        sleep(0.01)
        try:
            if mq.qsize() > 0:
                # Topic + Message
                msg = mq.get()
                logging.info("GET %s AND SENDING TO %s" % (msg, topic))
                producer.send(topic, msg)
                # Force buffer flush in order to send the message
                logging.info("MESSAGE SENT !")
                producer.flush()
        except Exception as e:
            logging.fatal(e, exc_info=True)

    producer.close()
    return


if __name__ == '__main__':
    # ENVIRONMENT
    if len(sys.argv) > 1 and str(sys.argv[1]) == 'production':
        env = 'production'

    if 'FLASK_ENV' not in os.environ:
        os.environ['FLASK_ENV'] = env
    if 'FLASK_APP' not in os.environ:
        os.environ['FLASK_APP'] = __file__ + '.py'

    if env == 'development':
        os.environ['FLASK_DEBUG'] = '1'
    else:
        os.environ['FLASK_DEBUG'] = '0'

    # LOGGING
    app.logger.removeHandler(default_handler)
    if env == 'production':
        logging.basicConfig(
            level=logging.WARNING
        )
    else:
        logging.basicConfig(
            level=logging.INFO
        )

    # CONFIGURATION
    app_config_raw = __load_config()
    app_config = app_config_raw[env]

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

    # WERKZEUG SERVER
    t_http_server_worker = threading.Thread(
        name='http_server_worker',
        daemon=True,
        target=http_server_worker
    )

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
            args=(topic, mq,)
        )
        threads.append(t_producer_worker)

    ###########################################################

    # Start
    logging.warning(__product__ + ' version ' + __version__ + ' (' + env + ') is starting...')

    # Starting threads
    for t in threads:
        t.start()
    t_http_server_worker.start()

    # Waiting threads...
    for t in threads:
        t.join()
    t_http_server_worker.join()

    logging.info('Bye !')
    exit(0)
