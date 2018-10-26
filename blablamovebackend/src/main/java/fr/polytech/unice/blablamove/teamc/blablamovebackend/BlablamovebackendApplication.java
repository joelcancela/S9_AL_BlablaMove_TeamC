package fr.polytech.unice.blablamove.teamc.blablamovebackend;

import kafka.consumer.Consumer;
import kafka.producer.Sender;
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
    }
}
