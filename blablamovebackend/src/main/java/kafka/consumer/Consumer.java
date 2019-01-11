package kafka.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
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

    private CountDownLatch latchDelivery = new CountDownLatch(3);
    private CountDownLatch latchUser = new CountDownLatch(3);

    void saveToInfluxDB(Point p) {
        BlablamovebackendApplication.influxDB.write(p);
    }

    @KafkaListener(topics = "${message.topic.delivery}", containerFactory = "KafkaListenerContainerFactory")
    public void listenDelivery(String message) {
        System.out.println("Received Message in topic 'delivery': " + message);
        Gson gson = new GsonBuilder().create();
        try {
            Message msg = gson.fromJson(message, Message.class);
            if (msg.getAction().equals("DELIVERY_INITIATED")) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
                Point p = Point.measurement("delivery_initiated").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("request", linkedTreeMap.get("request").toString())
                        .addField("city", linkedTreeMap.get("city").toString())
                        .addField("delivery_uuid", linkedTreeMap.get("delivery_uuid").toString())
                        .addField("time", linkedTreeMap.get("time").toString())
                        .build();
                saveToInfluxDB(p);
            } else if (msg.getAction().equals("DELIVERY_ISSUE")) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
                Point p = Point.measurement("delivery_issue").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("issue_type", linkedTreeMap.get("issue_type").toString())
                        .build();
                saveToInfluxDB(p);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error while parsing received message");
        }
        latchDelivery.countDown();
    }

    @KafkaListener(topics = "${message.topic.user}", containerFactory = "KafkaListenerContainerFactory")
    public void listenUser(String message) {
        System.out.println("Received Message in topic 'user': " + message);
        Gson gson = new GsonBuilder().create();
        try {
            Message msg = gson.fromJson(message, Message.class);
            if (msg.getAction().equals("USER_REGISTERED")) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
            } else if (msg.getAction().equals("USER_LOGGED_IN")) {
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
                Point p = Point.measurement("user_logged_in").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("request", linkedTreeMap.get("request").toString())
                        .addField("uuid", linkedTreeMap.get("uuid").toString())
                        .addField("time", linkedTreeMap.get("time").toString())
                        .build();
                saveToInfluxDB(p);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error while parsing received message");
        }
        latchUser.countDown();
    }

    public void latchDelivery(int time, TimeUnit unit) throws InterruptedException {
        this.latchDelivery.await(time,unit);
    }

    public void latchUser(int time, TimeUnit unit) throws InterruptedException {
        this.latchUser.await(time,unit);
    }
}
