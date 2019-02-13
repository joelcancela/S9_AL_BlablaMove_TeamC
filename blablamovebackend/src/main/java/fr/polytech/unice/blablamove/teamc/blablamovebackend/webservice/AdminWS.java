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
import java.util.HashMap;
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
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

		List<String> services_names = new ArrayList<>();
		HashMap<String, List<String>> services_regions = new HashMap<>();
		/*
		 *
		 * COLLECT ALL EXISTING SERVICES
		 *
		 */
		Query queryObject_services = new Query("Select distinct(service_name) as service_name from heartbeat", "blablamove");
		QueryResult queryResult_services = BlablamovebackendApplication.influxDB.query(queryObject_services);

		List<Heartbeat> heartbeats_service = resultMapper.toPOJO(queryResult_services, Heartbeat.class);
		List<QueryResult.Result> results_services_names = queryResult_services.getResults();
		for (QueryResult.Result r : results_services_names) {
			for (QueryResult.Series s: r.getSeries()) {
				if(s.getColumns().contains("service_name")) {
					List<List<Object>> values = s.getValues();
					for (List<Object> o : values) {
						String service = o.get(s.getColumns().indexOf("service_name")).toString();
						services_names.add(service);
						services_regions.put(service, new ArrayList<>());
					}
				}

			}
		}

		/*
		 *
		 * COLLECT ALL EXISTING REGIONS PER SERVICES
		 *
		 */
		for (String service : services_names) {
			Query queryObject_regions = new Query("Select distinct(region) as region from heartbeat where service_name = '" + service + "'", "blablamove");
			QueryResult queryResult_regions = BlablamovebackendApplication.influxDB.query(queryObject_regions);
			for (QueryResult.Result r : queryResult_regions.getResults()) {
				for (QueryResult.Series s: r.getSeries()) {
					if(s.getColumns().contains("region")) {
						List<List<Object>> values = s.getValues();
						for (List<Object> o : values) {
							String region = o.get(s.getColumns().indexOf("region")).toString();
							services_regions.get(service).add(region);
						}
					}

				}
			}
		}

		/*
		 *
		 * COLLECT LAST HEARBEAT FOR EACH REGION OF EACH SERVICE
		 *
		 */
		List<Heartbeat> heartbeat_replies = new ArrayList<>();
		for (String s : services_regions.keySet()) {
			for (String r : services_regions.get(s)) {
				Query queryObject_service = new Query("Select * from heartbeat where service_name = '" + s + "' and region = '" + r + "' order by desc limit 1", "blablamove");
				QueryResult queryResult_service = BlablamovebackendApplication.influxDB.query(queryObject_service);
				List<Heartbeat> heartbeats = resultMapper.toPOJO(queryResult_service, Heartbeat.class);
				heartbeat_replies.addAll(heartbeats);
			}
		}

		return heartbeat_replies;
	}

	private boolean instantIsBetweenDates(Instant instant, LocalDateTime start, LocalDateTime stop) {
		return (!instant.isBefore(start.toInstant(OffsetDateTime.now().getOffset()))) && (instant.isBefore(stop.toInstant(OffsetDateTime.now().getOffset())));
	}
}
