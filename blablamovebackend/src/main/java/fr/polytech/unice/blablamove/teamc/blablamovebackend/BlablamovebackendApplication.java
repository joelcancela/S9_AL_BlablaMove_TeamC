package fr.polytech.unice.blablamove.teamc.blablamovebackend;

import kafka.consumer.Consumer;
import kafka.producer.Sender;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Pong;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BlablamovebackendApplication {
	public static InfluxDB influxDB;

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(BlablamovebackendApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
		Consumer consumer = context.getBean(Consumer.class);
		Sender sender = context.getBean(Sender.class);

		consumer.latchDelivery(10, TimeUnit.SECONDS);
		consumer.latchUser(10, TimeUnit.SECONDS);
		System.out.println("Connecting to InfluxDB");
		influxDB = InfluxDBFactory.connect("http://influxdb:8086", "admin", "admin");
		influxDB.createRetentionPolicy("defaultPolicy", "baeldung", "30d", 1, true);
		influxDB.setLogLevel(InfluxDB.LogLevel.NONE);
		Pong response = influxDB.ping();
		if (response.getVersion().equalsIgnoreCase("unknown")) {
			System.out.println("Error pinging server.");
			return;
		} else {
			System.out.println("Connected to InfluxDB version " + response.getVersion());
		}

		if (!influxDB.databaseExists("blablamove")) {
			influxDB.createDatabase("blablamove");
			System.out.println("Will create blablamove database");
		}
		influxDB.setDatabase("blablamove");

		//influxDB.close();
	}
}
