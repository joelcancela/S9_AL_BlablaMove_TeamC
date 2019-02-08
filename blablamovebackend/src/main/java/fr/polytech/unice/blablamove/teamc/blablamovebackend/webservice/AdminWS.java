package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.ConnectionLog;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.Heartbeat;
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
	//TODO: nassim: heartbeat
	//si y'a le time un objet Transaction qui regroupe delivery et route events
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

	/**
	 * Returns the last heartbeats
	 * @return The last heartbeat
	 */
	@RequestMapping(path = "/heartbeats", method = RequestMethod.GET)
	public List<Heartbeat> getLastHeartbeats() {
		if(BlablamovebackendApplication.influxDB == null){//Avoid stack trace when front end requests and the
			// connection to influxdb is not ready
			return new ArrayList<>();
		}
		Query queryObject_delivery = new Query("Select * from heartbeat where service_name = 'Core Delivery' order by desc limit 1", "blablamove");
		Query queryObject_kpi = new Query("Select * from heartbeat where service_name = 'Core KPI' order by desc limit 1", "blablamove");
		Query queryObject_user = new Query("Select * from heartbeat where service_name = 'Core User' order by desc limit 1", "blablamove");
		QueryResult queryResult_delivery = BlablamovebackendApplication.influxDB.query(queryObject_delivery);
		QueryResult queryResult_kpi = BlablamovebackendApplication.influxDB.query(queryObject_kpi);
		QueryResult queryResult_user = BlablamovebackendApplication.influxDB.query(queryObject_user);
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<Heartbeat> heartbeat_replies = new ArrayList<>();
		List<Heartbeat> heartbeat_delivery = resultMapper.toPOJO(queryResult_delivery, Heartbeat.class);
		List<Heartbeat> heartbeat_kpi = resultMapper.toPOJO(queryResult_kpi, Heartbeat.class);
		List<Heartbeat> heartbeat_user = resultMapper.toPOJO(queryResult_user, Heartbeat.class);
		heartbeat_replies.addAll(heartbeat_delivery);
		heartbeat_replies.addAll(heartbeat_kpi);
		heartbeat_replies.addAll(heartbeat_user);
		System.out.println("Object : ");
		heartbeat_replies.stream().forEach(System.out::println);
		return heartbeat_replies;
	}

	private boolean instantIsBetweenDates(Instant instant, LocalDateTime start, LocalDateTime stop) {
		return (!instant.isBefore(start.toInstant(OffsetDateTime.now().getOffset()))) && (instant.isBefore(stop.toInstant(OffsetDateTime.now().getOffset())));
	}
}
