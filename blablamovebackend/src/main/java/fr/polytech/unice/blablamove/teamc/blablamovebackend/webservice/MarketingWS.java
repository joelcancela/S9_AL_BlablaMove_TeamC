package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.City;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.CityReport;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.DeliveryInitiated;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.DeliveryIssue;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.RouteCanceled;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.RouteCreated;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/marketing")
public class MarketingWS {

	private List<City> cities = new ArrayList<>();
	private List<CityReport> citiesReports = new ArrayList<>();

	// TODO: get data from DB
	public MarketingWS() {
		City marseille = new City("Marseille");
		City antibes = new City("Antibes");
		City toulon = new City("Toulon");
		City aix = new City("Aix-en-provence");
		City nice = new City("Nice");
		cities.add(marseille);
		cities.add(antibes);
		cities.add(toulon);
		cities.add(aix);
		cities.add(nice);
	}

	@RequestMapping(path = "/cities", method = RequestMethod.GET)
	public List<City> getAllActiveCities() {
		//TODO: joel will do
		return cities;
	}

	@RequestMapping(path = "/mostActiveCities", method = RequestMethod.GET)
	public List<CityReport> getMostActiveCitiesAllTime() {
		Query queryObject = new Query("Select * from delivery_initiated", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<DeliveryInitiated> deliveryInitiatedList = resultMapper
				.toPOJO(queryResult, DeliveryInitiated.class);

		Map<String, Long> counted = deliveryInitiatedList.stream()
				.collect(Collectors.groupingBy(DeliveryInitiated::getCity,
						Collectors.counting()));
		List<CityReport> reports = new ArrayList<>();
		for (Map.Entry<String, Long> entry : counted.entrySet()) {
			reports.add(new CityReport(new City(entry.getKey()), entry.getValue()));
		}

		return reports.stream().sorted((f1, f2) -> Long.compare(f2.getTransactionCount(),
				f1.getTransactionCount())).collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Returns the delivery issues of the last 24 hours.
	 * @return The delivery issues of the last 24 hours.
	 */
	@RequestMapping(path = "/deliveryIssues", method = RequestMethod.GET)
	public List<DeliveryIssue> getLast24hDeliveryIssues() {
		Query queryObject = new Query("Select * from delivery_issue", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		System.out.println("queryResult : " + queryResult);
		List<DeliveryIssue> deliveryIssueList = resultMapper
				.toPOJO(queryResult, DeliveryIssue.class);

		LocalDateTime stop = LocalDateTime.now().minusHours(0);
		LocalDateTime start = LocalDateTime.now().minusHours(24).withSecond(0).withMinute(0).withNano(0);

        return deliveryIssueList.stream().filter(deliveryIssue -> instantIsBetweenDates(deliveryIssue.getTime(), start, stop)).collect(Collectors.toList());
	}

	/**
	 * Returns the last heartbeat for the marketing core
	 * @return The last heartbeat
	 */
	@RequestMapping(path = "/heartbeat", method = RequestMethod.GET)
	public void getLastHeartbeat() {
		Query queryObject = new Query("Select * from heartbeat", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		System.out.println("queryResult : " + queryResult);
		//List<DeliveryIssue> deliveryIssueList = resultMapper
		//		.toPOJO(queryResult, DeliveryIssue.class);

		LocalDateTime stop = LocalDateTime.now().minusHours(0);
		LocalDateTime start = LocalDateTime.now().minusHours(24).withSecond(0).withMinute(0).withNano(0);

		//return deliveryIssueList.stream().filter(deliveryIssue -> instantIsBetweenDates(deliveryIssue.getTime(), start, stop)).collect(Collectors.toList());
	}

	/**
	 * Returns the delivery issues in a specific timeframe.
	 * @param from The beginning of the timeframe.
	 * @param to The end of the timeframe.
	 * @return The issues in the specific timeframe.
	 */
	@RequestMapping(path = "/specificDeliveryIssues", method = RequestMethod.GET)
	public List<DeliveryIssue> getIssuesByTimeframe(Date from, Date to) {
		Query queryObject = new Query("Select * from delivery_issue", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<DeliveryIssue> deliveryIssueList = resultMapper
				.toPOJO(queryResult, DeliveryIssue.class);

        return deliveryIssueList.stream().filter(
        		deliveryIssue -> !instantIsBetweenDates(
        				deliveryIssue.getTime(),
						LocalDateTime.ofInstant(
                                to.toInstant(), ZoneOffset.UTC
						),
						LocalDateTime.ofInstant(
                                from.toInstant(), ZoneOffset.UTC
                        )
				)
		).collect(Collectors.toList());
	}

	/**
	 * Returns the routes created in a specific timeframe.
	 * @param from The beginning of the timeframe.
	 * @param to The end of the timeframe.
	 * @return The routes created in the specific timeframe.
	 */
	@RequestMapping(path = "/specificCreatedRoutes", method = RequestMethod.GET)
	public List<RouteCreated> GetRoutesCreatedByTimeframe(Date from, Date to) {
		Query queryObject = new Query("Select * from created_routes", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<RouteCreated> deliveryIssueList = resultMapper
				.toPOJO(queryResult, RouteCreated.class);

		return deliveryIssueList.stream().filter(
				deliveryIssue -> !instantIsBetweenDates(
						deliveryIssue.getTime(),
						LocalDateTime.ofInstant(
								to.toInstant(), ZoneOffset.UTC
						),
						LocalDateTime.ofInstant(
								from.toInstant(), ZoneOffset.UTC
						)
				)
		).collect(Collectors.toList());
	}

	/**
	 * Returns the routes canceled in a specific timeframe.
	 * @param from The beginning of the timeframe.
	 * @param to The end of the timeframe.
	 * @return The routes canceled in the specific timeframe.
	 */
	@RequestMapping(path = "/specificCanceledRoutes", method = RequestMethod.GET)
	public List<RouteCanceled> GetRoutesCanceledByTimeframe(Date from, Date to) {
		Query queryObject = new Query("Select * from canceled_routes", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<RouteCanceled> deliveryIssueList = resultMapper
				.toPOJO(queryResult, RouteCanceled.class);

		return deliveryIssueList.stream().filter(
				deliveryIssue -> !instantIsBetweenDates(
						deliveryIssue.getTime(),
						LocalDateTime.ofInstant(
								to.toInstant(), ZoneOffset.UTC
						),
						LocalDateTime.ofInstant(
								from.toInstant(), ZoneOffset.UTC
						)
				)
		).collect(Collectors.toList());
	}

	private boolean instantIsBetweenDates(Instant instant, LocalDateTime start, LocalDateTime stop) {
		return (!instant.isBefore(start.toInstant(OffsetDateTime.now().getOffset()))) && (instant.isBefore(stop.toInstant(OffsetDateTime.now().getOffset())));
	}
}