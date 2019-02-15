package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Class RouteCreated
 *
 * @author Tanguy Invernizzi
 */
@Measurement(name = "route_created")
public class RouteCreated {

    /**
     * The id of this route.
     */
    @Column(name = "route_uuid")
    private String route_uuid;

    /**
     * The city where the delivery started.
     */
    @Column(name = "initial_city")
    private String initialCity;

    /**
     * The city where the delivery ended
     */
    @Column(name = "end_city")
    private String endCity;

    /**
     * The time at which the route has been created.
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

    public String getInitialCity() {
        return initialCity;
    }

    public void setInitialCity(String initialCity) {
        this.initialCity = initialCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
