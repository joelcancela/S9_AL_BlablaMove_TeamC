package fr.polytech.unice.blablamove.teamc.blablamovebackend.model;

import java.time.LocalDateTime;

/**
 * Class ConnectionLog
 *
 * @author Joël CANCELA VAZ
 */
public class ConnectionLog {
	private LocalDateTime localDateTime;
	private long totalUsersConnected;

	public ConnectionLog(LocalDateTime localDateTime, long totalUsersConnected) {
		this.localDateTime = localDateTime;
		this.totalUsersConnected = totalUsersConnected;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public long getTotalUsersConnected() {
		return totalUsersConnected;
	}

	public void setTotalUsersConnected(long totalUsersConnected) {
		this.totalUsersConnected = totalUsersConnected;
	}
}
