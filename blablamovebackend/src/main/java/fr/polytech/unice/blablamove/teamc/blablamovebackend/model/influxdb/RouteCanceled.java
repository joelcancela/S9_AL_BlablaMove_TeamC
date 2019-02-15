package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Class RouteCanceled
 *
 * @author Tanguy Invernizzi
 */
@Measurement(name = "route_canceled")
public class RouteCanceled {

    /**
     * The id of this route.
     */
    @Column(name = "route_uuid")
    private String route_uuid;

    /**
     * The time at which the route has been canceled.
     */
    @Column(name = "time")
    private Instant time;

    public Instant getTime() {
        return time;
    }

    public String getRoute_uuid() {
        return route_uuid;
    }

    public void setRoute_uuid(String route_uuid) {
        this.route_uuid = route_uuid;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
