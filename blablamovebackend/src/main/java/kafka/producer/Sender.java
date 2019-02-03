package kafka.producer;


import kafka.consumer.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;


public class Sender {

    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Value(value = "${message.topic.heartbeat}")
    private String topic;

    public Sender() {
    }

    public void send(Message message) {
        LOG.info("sending message='{}' to topic='{}'", message, topic);
        kafkaTemplate.send(topic, message);
    }
}