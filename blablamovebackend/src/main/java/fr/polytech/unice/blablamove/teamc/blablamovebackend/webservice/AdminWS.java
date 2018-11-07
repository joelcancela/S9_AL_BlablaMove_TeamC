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
import java.util.Random;

/**
 * Class AdminWS
 *
 * @author Joël CANCELA VAZ
 */
@RestController
@RequestMapping(path = "/admin")
public class AdminWS {

	private List<ConnectionLog> connections = new ArrayList<>();

	//TODO: Get from db
	public AdminWS() {
		Random rand = new Random();
		for (int i = 0; i < 24; i++) {
			if (LocalDateTime.now().minusHours(i).getHour() <= 13 && LocalDateTime.now().minusHours(i).getHour() >= 11) {//Simuler pic entre 11h et 13h
				connections.add(new ConnectionLog(LocalDateTime.now().minusHours(i), rand.nextInt(2000) + 1500));
			} else {
				connections.add(new ConnectionLog(LocalDateTime.now().minusHours(i), rand.nextInt(2000)));
			}
		}
	}

	@RequestMapping(path = "/last24Connections", method = RequestMethod.GET)
	public List<ConnectionLog> getLast24Connections() {
		Query queryObject = new Query("Select * from user_logged_in", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<UserLoggedIn> userLoggedInList = resultMapper
				.toPOJO(queryResult, UserLoggedIn.class);
		for (int i = 0; i < 24; i++) {
			LocalDateTime stop = LocalDateTime.now().minusHours(i);
			LocalDateTime start = LocalDateTime.now().minusHours(i++);
			long numberOfUsers =
					userLoggedInList.stream().filter(userLoggedIn -> instantIsBetweenDates(userLoggedIn.getTime(),
							start, stop)).count();
			connections.add(new ConnectionLog(start, numberOfUsers));
		}

		return connections;
	}

	private boolean instantIsBetweenDates(Instant instant, LocalDateTime start, LocalDateTime stop) {
		return (!instant.isBefore(start.toInstant(OffsetDateTime.now().getOffset()))) && (instant.isBefore(stop.toInstant(OffsetDateTime.now().getOffset())));
	}
}
