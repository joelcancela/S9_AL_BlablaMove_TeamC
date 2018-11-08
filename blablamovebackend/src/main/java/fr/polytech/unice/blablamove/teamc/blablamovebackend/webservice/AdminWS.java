package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.ConnectionLog;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.UserLoggedIn;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class AdminWS
 *
 * @author Joël CANCELA VAZ
 */
@RestController
@RequestMapping(path = "/admin")
public class AdminWS {

	public AdminWS() {
	}

	@RequestMapping(path = "/last24Connections", method = RequestMethod.GET)
	public List<ConnectionLog> getLast24Connections() {
		List<ConnectionLog> connections = new ArrayList<>();
		Query queryObject = new Query("Select * from user_logged_in", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<UserLoggedIn> userLoggedInList = resultMapper
				.toPOJO(queryResult, UserLoggedIn.class);
		for (int i = 0; i < 24; i++) {
			LocalDateTime stop = LocalDateTime.now().minusHours(i);
			LocalDateTime display = LocalDateTime.now().minusHours(i).withSecond(0).withMinute(0).withNano(0);
			LocalDateTime start = LocalDateTime.now().minusHours(i+1).withSecond(0).withMinute(0).withNano(0);
			long numberOfUsers =
					userLoggedInList.stream().filter(userLoggedIn -> instantIsBetweenDates(userLoggedIn.getTime(),
							start, stop)).count();
			connections.add(new ConnectionLog(display, numberOfUsers));
		}

		return connections;
	}

	private boolean instantIsBetweenDates(Instant instant, LocalDateTime start, LocalDateTime stop) {
		return (!instant.isBefore(start.toInstant(OffsetDateTime.now().getOffset()))) && (instant.isBefore(stop.toInstant(OffsetDateTime.now().getOffset())));
	}
}
