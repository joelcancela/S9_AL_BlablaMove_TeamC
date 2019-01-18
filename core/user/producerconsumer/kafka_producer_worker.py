#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
import queue
import threading
from time import sleep
from kafka import KafkaProducer


def kafka_producer_worker(
        t_stop_event: threading.Event,
        bootstrap_servers: str,
        topic: str,
        mq: queue.Queue):
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
                print("GET %s AND SENDING TO %s" % (msg, topic))
                producer.send(topic, msg)
                # Force buffer flush in order to send the message
                print("MESSAGE SENT !")
                producer.flush()
        except Exception as e:
            print(e)

    producer.close()
    return
