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
     * The id of this delivery.
     */
    @Column(name = "delivery_uuid")
    private String delivery_uuid;

    /**
     * The time at which the issue happened.
     */
    @Column(name = "time")
    private Instant time;

    public Instant getTime() {
        return time;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getDelivery_uuid() {
        return delivery_uuid;
    }

    public void setDelivery_uuid(String delivery_uuid) {
        this.delivery_uuid = delivery_uuid;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
