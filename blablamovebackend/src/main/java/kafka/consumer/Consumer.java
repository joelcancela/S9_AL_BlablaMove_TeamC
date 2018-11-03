package kafka.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Consumer {

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    private CountDownLatch latch = new CountDownLatch(3);

    @KafkaListener(topics = "${message.topic.name}", containerFactory = "KafkaListenerContainerFactory")
    public void listen(String message) {
        System.out.println("Received Message in topic 'topic': " + message);
        if (message.equals("USER_CREATED")) {
            InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
            influxDB.setDatabase("blablamove");
            long free = Runtime.getRuntime().freeMemory();
            Point p = Point.measurement("user_created").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).addField("free", free).build();
            influxDB.write(p);
            influxDB.close();
        } else {
            Gson gson = new GsonBuilder().create();
            Message msg = gson.fromJson(message, Message.class);
            System.out.println(msg.toString());
            if (msg.getAction().equals("USER_REGISTERED")) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
                System.out.println(linkedTreeMap.toString());
                System.out.println("Request is : " + linkedTreeMap.get("request"));
            }
        }
        latch.countDown();
    }

    public void latch(int time, TimeUnit unit) throws InterruptedException {
        this.latch.await(time,unit);
    }
}
