package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.DeliveryIssue;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;

/**
 * MarketingWS tests. Kafka and InfluxDB needs to be run in the background for these tests to work.
 * This will wipe the database.
 */
public class MarketingWSTest {

    @Before
    public void init() throws Exception {
        BlablamovebackendApplication.main(new String[0]);

        // Wipe database
        Query queryObject = new Query("DROP SERIES FROM /.*/", "blablamove");
        BlablamovebackendApplication.influxDB.query(queryObject);

        // Fill database
        Point p = Point.measurement("delivery_issue").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("issue_type", "test")
                .addField("time", System.currentTimeMillis())
                .build();
        BlablamovebackendApplication.influxDB.write(p);

        p = Point.measurement("delivery_issue").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("issue_type", "test")
                .addField("time", System.currentTimeMillis())
                .build();
        BlablamovebackendApplication.influxDB.write(p);

        p = Point.measurement("delivery_issue").time(LocalDateTime.now().minusDays(2).toEpochSecond(ZoneOffset.UTC), TimeUnit.SECONDS)
                .addField("issue_type", "test")
                .addField("time", LocalDateTime.now().minusDays(2).toString())
                .build();

        BlablamovebackendApplication.influxDB.write(p);

        queryObject = new Query("Select * from delivery_issue", "blablamove");
        BlablamovebackendApplication.influxDB.query(queryObject);
    }

    @Test
    public void getDeliveryIssues() {
        MarketingWS marketingWS = new MarketingWS();
        List<DeliveryIssue> deliveries = marketingWS.getLast24hDeliveryIssues();
        assertEquals(deliveries.size(),2);

        deliveries = marketingWS.getIssuesByTimeframe(Date.from(LocalDateTime.now().minusDays(3).toInstant(ZoneOffset.UTC)), Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        assertEquals(deliveries.size(),3);
    }
}