package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Class DeliveredItem
 *
 * @author Tanguy Invernizzi
 */
@Measurement(name = "delivery_item")
public class DeliveredItem {

    /**
     * The type of the item.
     */
    @Column(name = "item_type")
    private String itemType;

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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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
