package kafka.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Consumer {

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    private CountDownLatch latchDelivery = new CountDownLatch(3);
    private CountDownLatch latchUser = new CountDownLatch(3);
    private CountDownLatch latchHeartBeat = new CountDownLatch(3);

    /**
     * This method is called whenever a message is received on the Kafka topic associated with deliveries.
     *
     * @param message The message received on the message bus.
     */
    @KafkaListener(topics = "${message.topic.delivery}", containerFactory = "KafkaListenerContainerFactory")
    public void listenDelivery(String message) {
        LOG.info("Received Message in topic 'delivery': " + message);
        Gson gson = new GsonBuilder().create();
        try {
            Message msg = gson.fromJson(message, Message.class);
            if (msg.getAction().equals("DELIVERY_INITIATED")) {
                storeDeliveryInitiation(msg);
            } else if (msg.getAction().equals("DELIVERY_ISSUE")) {
                storeDeliveryIssue(msg);
            } else if (msg.getAction().equals("ROUTE_CREATED")) {
                storeRouteCreated(msg);
            } else if (msg.getAction().equals("ROUTE_CANCELED")) {
                storeRouteCanceled(msg);
            } else if (msg.getAction().equals("DELIVERY_ITEM")) {
            storeDeliveryItem(msg);
            }
        } catch (JsonSyntaxException e) {
            LOG.error("Error while parsing received message");
        }
        latchDelivery.countDown();
    }

    /**
     * Stores a new delivery item in the Influx Database.
     *
     * @param msg The kafka message associated with this delivery item.
     */
    private void storeDeliveryItem(Message msg) {
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
        Point p = Point.measurement("delivery_item").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("item_type", linkedTreeMap.get("item_type").toString())
                .addField("delivery_uuid", linkedTreeMap.get("delivery_uuid").toString())
                .addField("time", linkedTreeMap.get("time").toString())
                .build();
        saveToInfluxDB(p);
    }

    /**
     * This method is called whenever a message is received on the Kafka topic associated with heartbeats.
     *
     * @param message The message received on the message bus.
     */
    @KafkaListener(topics = "${message.topic.heartbeat}", containerFactory = "KafkaListenerContainerFactory")
    public void listenHeartBeat(String message) {
        LOG.info("Received Message in topic 'heartbeat': " + message);
        Gson gson = new GsonBuilder().create();
        try {
            Message msg = gson.fromJson(message, Message.class);
            if (msg.getAction().equals("HEARTBEAT_REPLY")) {
                processHeartbeat(msg);
                //LOG.info("HEARTBEAT RECEIVED : ", heartbeat_reply);
            }
        } catch (JsonSyntaxException e) {
            LOG.error("Error while parsing received message");
        }
        latchHeartBeat.countDown();
    }

    private void processHeartbeat(Message msg) {
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
        HEARTBEAT_REPLY heartbeat_reply = new HEARTBEAT_REPLY();
        heartbeat_reply.setRequest((Double)linkedTreeMap.get("request"));
        heartbeat_reply.setTimestamp((Double)linkedTreeMap.get("timestamp"));
        heartbeat_reply.setService_name((String)linkedTreeMap.get("service_name"));
        heartbeat_reply.setRegion((String) linkedTreeMap.get("region"));
        LOG.info("RECEIVED : " + heartbeat_reply.toString());
        Point p = Point.measurement("heartbeat")
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField("service_name", heartbeat_reply.getService_name())
                        .addField("region", heartbeat_reply.getRegion())
                        .build();
        saveToInfluxDB(p);
    }

    /**
     * Stores a new delivery initiation in the Influx Database.
     *
     * @param msg The kafka message associated with this delivery.
     */
    private void storeDeliveryInitiation(Message msg) {
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
        Point p = Point.measurement("delivery_initiated").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("request", linkedTreeMap.get("request").toString())
                .addField("city", linkedTreeMap.get("city").toString())
                .addField("delivery_uuid", linkedTreeMap.get("delivery_uuid").toString())
                .addField("time", linkedTreeMap.get("time").toString())
                .addField("route_uuid", linkedTreeMap.get("route_uuid").toString())
                .build();
        saveToInfluxDB(p);
    }

    /**
     * Stores a new delivery issue in the Influx Database.
     *
     * @param msg The kafka message associated with this delivery.
     */
    private void storeDeliveryIssue(Message msg) {
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
        Point p = Point.measurement("delivery_issue").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("issue_type", linkedTreeMap.get("issue_type").toString())
                .addField("delivery_uuid",linkedTreeMap.get("delivery_uuid").toString())
                .build();
        saveToInfluxDB(p);
    }

    /**
     * Stores a new route creation event in the Influx Database.
     *
     * @param msg The kafka message associated with this delivery.
     */
    private void storeRouteCreated(Message msg) {
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
        Point p = Point.measurement("route_created").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("route_uuid", linkedTreeMap.get("route_uuid").toString())
                .addField("initial_city", linkedTreeMap.get("initial_city").toString())
                .addField("end_city", linkedTreeMap.get("end_city").toString())
                .build();
        saveToInfluxDB(p);
    }

    /**
     * Stores a new route creation event in the Influx Database.
     *
     * @param msg The kafka message associated with this delivery.
     */
    private void storeRouteCanceled(Message msg) {
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) msg.getMessage();
        Point p = Point.measurement("route_canceled").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("route_uuid", linkedTreeMap.get("route_uuid").toString())
                .build();
        saveToInfluxDB(p);
    }

    @KafkaListener(topics = "${message.topic.user}", containerFactory = "KafkaListenerContainerFactory")
    public void listenUser(String message) {
        LOG.info("Received Message in topic 'user': " + message);
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
            LOG.error("Error while parsing received message");
        }
        latchUser.countDown();
    }

    public void latchDelivery(int time, TimeUnit unit) throws InterruptedException {
        this.latchDelivery.await(time, unit);
    }

    public void latchUser(int time, TimeUnit unit) throws InterruptedException {
        this.latchUser.await(time, unit);
    }

    private void saveToInfluxDB(Point p) {
        try {
            BlablamovebackendApplication.influxDB.write(p);
        } catch (NullPointerException e) {
            LOG.error("Tried to write into influxdb before connection");
        }
    }

}
