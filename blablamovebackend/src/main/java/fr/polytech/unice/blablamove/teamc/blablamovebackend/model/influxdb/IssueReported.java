package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * Class IssueReported
 *
 * @author Joël CANCELA VAZ
 */
@Measurement(name = "issue_reported")
public class IssueReported {
	@Column(name = "issue_uuid")
	public String issue_uuid;

	@Column(name = "time")
	public Instant time;

	public IssueReported(String issue_uuid, Instant time) {
		this.issue_uuid = issue_uuid;
		this.time = time;
	}

	public IssueReported() {
	}

	public Instant getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "IssueReported{" +
				"time=" + time +
				'}';
	}
}
