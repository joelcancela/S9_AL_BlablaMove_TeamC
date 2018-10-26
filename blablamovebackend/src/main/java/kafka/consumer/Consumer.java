package kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Consumer {

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    private CountDownLatch latch = new CountDownLatch(3);

    @Value("test")
    private String topic;

    @KafkaListener(topics = "${message.topic.name}", containerFactory = "KafkaListenerContainerFactory")
    public void listen(String message) {
        System.out.println("Received Message in topic 'test': " + message);
        latch.countDown();
    }

    public void latch(int time, TimeUnit unit) throws InterruptedException {
        this.latch.await(time,unit);
    }
}
