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
     * The type of the issue.
     */
    @Column(name = "issue_type")
    private String issueType;

    /**
     * The id identifying the delivery associated with this route.
     */
    @Column(name = "delivery_uuid")
    private String delivery_uuid;

    /**
     * The time at which the route has been created.
     */
    @Column(name = "time")
    private Instant time;

    public Instant getTime() {
        return time;
    }
}
