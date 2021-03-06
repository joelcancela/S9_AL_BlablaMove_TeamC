package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.City;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.CityReport;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.*;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory.getLogger(MarketingWS.class);

	private List<City> cities = new ArrayList<>();
	private List<CityReport> citiesReports = new ArrayList<>();

	//TODO: joel:get cities from db
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
		List<City> cities = new ArrayList<>();
		Query queryObject = new Query("Select distinct(city) as city from delivery_initiated", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);
		List<QueryResult.Result> results_cities = queryResult.getResults();
		for (QueryResult.Result r : results_cities) {
			try {
				for (QueryResult.Series s : r.getSeries()) {
					if (s.getColumns().contains("city")) {
						List<List<Object>> values = s.getValues();
						for (List<Object> o : values) {
							String city = o.get(s.getColumns().indexOf("city")).toString();
							cities.add(new City(city));
						}
					}

				}
			} catch (NullPointerException e) {
				LOG.info("No city can be found into database, returning empty list");
			}
		}
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
	@RequestMapping(path = "/last24hDeliveryIssues", method = RequestMethod.GET)
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
     * Returns the created routes of the last 24 hours.
     * @return The created routes of the last 24 hours.
     */
    @RequestMapping(path = "/last24hCreatedRoutes", method = RequestMethod.GET)
    public List<RouteCreated> getLast24hCreatedRoutes() {
        Query queryObject = new Query("Select * from route_created", "blablamove");
        QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        System.out.println("queryResult : " + queryResult);
        List<RouteCreated> routeCreatedList = resultMapper
                .toPOJO(queryResult, RouteCreated.class);

        LocalDateTime stop = LocalDateTime.now().minusHours(0);
        LocalDateTime start = LocalDateTime.now().minusHours(24).withSecond(0).withMinute(0).withNano(0);

        return routeCreatedList.stream().filter(routeCreated -> instantIsBetweenDates(routeCreated.getTime(), start, stop)).collect(Collectors.toList());
    }

	/**
	 * Returns the routes created in a specific timeframe.
	 * @param from The beginning of the timeframe.
	 * @param to The end of the timeframe.
	 * @return The routes created in the specific timeframe.
	 */
	@RequestMapping(path = "/specificCreatedRoutes", method = RequestMethod.GET)
	public List<RouteCreated> getRoutesCreatedByTimeframe(Date from, Date to) {
		Query queryObject = new Query("Select * from route_created", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<RouteCreated> routeCreatedList = resultMapper
				.toPOJO(queryResult, RouteCreated.class);

		return routeCreatedList.stream().filter(
				routeCreated -> !instantIsBetweenDates(
						routeCreated.getTime(),
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
	public List<RouteCanceled> getRoutesCanceledByTimeframe(Date from, Date to) {
		Query queryObject = new Query("Select * from route_canceled", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<RouteCanceled> routeCanceledList = resultMapper
				.toPOJO(queryResult, RouteCanceled.class);

		return routeCanceledList.stream().filter(
				routeCanceled -> !instantIsBetweenDates(
						routeCanceled.getTime(),
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
     * Returns the canceled routes of the last 24 hours.
     * @return The canceled routes of the last 24 hours.
     */
    @RequestMapping(path = "/last24hCanceledRoutes", method = RequestMethod.GET)
    public List<RouteCanceled> getLast24hCanceledRoutes() {
        Query queryObject = new Query("Select * from route_canceled", "blablamove");
        QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        System.out.println("queryResult : " + queryResult);
        List<RouteCanceled> routeCanceledList = resultMapper
                .toPOJO(queryResult, RouteCanceled.class);

        LocalDateTime stop = LocalDateTime.now().minusHours(0);
        LocalDateTime start = LocalDateTime.now().minusHours(24).withSecond(0).withMinute(0).withNano(0);

        return routeCanceledList.stream().filter(routeCanceled -> instantIsBetweenDates(routeCanceled.getTime(), start, stop)).collect(Collectors.toList());
    }

    /**
     * Returns the delivered items of the last 24 hours.
     * @return The delivered items of the last 24 hours.
     */
    @RequestMapping(path = "/last24hDeliveredItems", method = RequestMethod.GET)
    public List<DeliveredItem> getLast24hDeliveredItems() {
        Query queryObject = new Query("Select * from delivery_item", "blablamove");
        QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        System.out.println("queryResult : " + queryResult);
        List<DeliveredItem> deliveredItemList = resultMapper
                .toPOJO(queryResult, DeliveredItem.class);

        LocalDateTime stop = LocalDateTime.now().minusHours(0);
        LocalDateTime start = LocalDateTime.now().minusHours(24).withSecond(0).withMinute(0).withNano(0);

        return deliveredItemList.stream().filter(deliveredItem -> instantIsBetweenDates(deliveredItem.getTime(), start, stop)).collect(Collectors.toList());
    }

	private boolean instantIsBetweenDates(Instant instant, LocalDateTime start, LocalDateTime stop) {
		return (!instant.isBefore(start.toInstant(OffsetDateTime.now().getOffset()))) && (instant.isBefore(stop.toInstant(OffsetDateTime.now().getOffset())));
	}
}