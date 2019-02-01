package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Class DeliveryInitiated
 *
 * @author Joël CANCELA VAZ
 */
@Measurement(name = "delivery_initiated")
public class DeliveryInitiated {

	@Column(name = "time")
	private Instant time;

	@Column(name = "city")
	private String city;

	/**
	 * The id of this delivery.
	 */
	@Column(name = "delivery_uuid")
	private String delivery_uuid;

	public Instant getTime() {
		return time;
	}

	public String getCity() {
		return city;
	}

	public String getDelivery_uuid() {
		return delivery_uuid;
	}
}
