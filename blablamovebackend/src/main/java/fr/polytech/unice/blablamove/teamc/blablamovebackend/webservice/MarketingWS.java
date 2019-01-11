package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.City;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.CityReport;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.DeliveryInitiated;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.DeliveryIssue;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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


	@RequestMapping(path = "/deliveryIssues", method = RequestMethod.GET)
	public List<DeliveryIssue> getLast24hDeliveryIssues() {
		// TODO
		return null;
	}

	@RequestMapping(path = "/specificDeliveryIssues", method = RequestMethod.GET)
	public List<DeliveryIssue> getIssuesByTimeframe(Date from, Date to) {
		// TODO
		return null;
	}
}