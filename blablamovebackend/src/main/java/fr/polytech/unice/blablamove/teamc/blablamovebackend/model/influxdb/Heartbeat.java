package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "heartbeat")
public class Heartbeat {
    @Column(name = "time")
    public Instant time;
    @Column(name = "service_name")
    public String service_name;
    @Column(name = "region")
    public String region;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "time=" + time +
                ", service_name='" + service_name + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
