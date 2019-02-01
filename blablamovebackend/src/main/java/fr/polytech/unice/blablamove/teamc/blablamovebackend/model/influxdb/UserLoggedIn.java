package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Class UserLoggedIn
 *
 * @author Joël CANCELA VAZ
 */
@Measurement(name = "user_logged_in")
public class UserLoggedIn {

	@Column(name = "time")
	private Instant time;

	@Column(name = "uuid")
	private String uuid;

	public Instant getTime() {
		return time;
	}

	public String getUuid() {
		return uuid;
	}
}
