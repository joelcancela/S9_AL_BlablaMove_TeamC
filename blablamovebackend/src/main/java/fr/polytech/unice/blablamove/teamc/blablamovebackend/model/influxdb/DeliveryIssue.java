package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

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


}
