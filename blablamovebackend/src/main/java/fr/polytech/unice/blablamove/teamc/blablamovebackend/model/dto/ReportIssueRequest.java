package fr.polytech.unice.blablamove.teamc.blablamovebackend.model.dto;

/**
 * Class ReportIssueRequest
 *
 * @author Joël CANCELA VAZ
 */
public class ReportIssueRequest {
	private String uuid;

	public ReportIssueRequest(String uuid) {
		this.uuid = uuid;
	}

	public ReportIssueRequest() {
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "ReportIssueRequest{" +
				"uuid='" + uuid + '\'' +
				'}';
	}
}
