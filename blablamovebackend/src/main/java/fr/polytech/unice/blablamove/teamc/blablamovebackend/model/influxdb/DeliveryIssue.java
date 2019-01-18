package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Class DeliveryIssue
 *
 * @author Tanguy Invernizzi
 */
@Measurement(name = "delivery_issue")
public class DeliveryIssue {

    /**
     * The type of the issue.
     */
    @Column(name = "issue_type")
    private String issueType;


    /**
     * The time at which the issue happened.
     */
    @Column(name = "time")
    private Instant time;

    public Instant getTime() {
        return time;
    }
}
