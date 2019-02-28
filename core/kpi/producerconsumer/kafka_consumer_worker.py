#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import json
import threading
from kafka import KafkaConsumer
from message.make_kafka_message import make_kafka_message


def kafka_consumer_worker(
        t_stop_event: threading.Event,
        bootstrap_servers: str,
        topic: str,
        region: str,
        service_name: str,
        threads_mq: dict):
    """
    Kafka Generic Message Consumer, as thread worker
    Push back messages to shared Queue
    :param t_stop_event: threading.Event
    :param bootstrap_servers: str
    :param topic: str
    :param region:
    :param service_name: str
    :param threads_mq: dict
    :return:
    """

    # region
    if len(region) == 0:
        region = "world"

    # Client
    consumer = KafkaConsumer(topic,
                             bootstrap_servers=bootstrap_servers,
                             value_deserializer=lambda item: json.loads(item.decode('utf-8')))

    while not t_stop_event.is_set():
        try:
            # Message loop
            for message in consumer:
                print("READING MESSAGE %s:%d:%d: key=%s value=%s" % (
                    message.topic,
                    message.partition,
                    message.offset,
                    message.key,
                    message.value)
                )

                # simple sanitizer
                if 'action' not in message.value:
                    print("MALFORMED MESSAGE value=%s SKIPPING" % (message.value,))
                    continue

                # Action switch
                if str(message.value["action"]).upper() == 'HEARTBEAT_BROADCAST':
                    print("SENDING HEARTBEAT FOR " + service_name)
                    # Send
                    hb, request_id = make_kafka_message(
                        action='HEARTBEAT_REPLY',
                        message={
                            'service_name': str(service_name),
                            'region': str(region),
                            'timestamp': int(datetime.datetime.now().timestamp())
                        }
                    )
                    threads_mq['heartbeat'].put(hb)
        except Exception as e:
            print(e)

    consumer.close()
    return
