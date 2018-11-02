package fr.polytech.unice.blablamove.teamc.blablamovebackend;

import kafka.consumer.Consumer;
import kafka.producer.Sender;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BlablamovebackendApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(BlablamovebackendApplication.class, args);
        Consumer consumer = context.getBean(Consumer.class);
        Sender sender = context.getBean(Sender.class);

        sender.send("Hello");
        consumer.latch(10, TimeUnit.SECONDS);

        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "admin", "admin");
        influxDB.createRetentionPolicy("defaultPolicy", "baeldung", "30d", 1, true);
        influxDB.setLogLevel(InfluxDB.LogLevel.FULL);
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            System.out.println("Error pinging server.");
            return;
        } else {
            System.out.println(response.getVersion());
        }


        if (!influxDB.databaseExists("blablamove")) {
            influxDB.createDatabase("blablamove");
            System.out.println("Will create blablamove database");
        } else {
            influxDB.setDatabase("blablamove");
            System.out.println("Database blablamove exists");
        }
        influxDB.close();
    }
}
