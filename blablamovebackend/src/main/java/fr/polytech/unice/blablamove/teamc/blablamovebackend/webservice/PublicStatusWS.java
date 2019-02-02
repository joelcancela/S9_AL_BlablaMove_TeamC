package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.BlablamovebackendApplication;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.dto.ReportIssueRequest;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.influxdb.IssueReported;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/publicstatus")
public class PublicStatusWS {
	@RequestMapping(path = "/last24hIncidents")
	public List<Pair<LocalDateTime, Long>> getLast24hIncidents() {
		List<Pair<LocalDateTime, Long>> report = new ArrayList<>();
		Query queryObject = new Query("Select * from issue_reported ORDER BY time DESC", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<IssueReported> issueReportedList = resultMapper
				.toPOJO(queryResult, IssueReported.class);
		LocalDateTime date = LocalDateTime.now();
		for (int i = 24; i > 0; i--) {
			LocalDateTime finalDate = date;
			long incident = 0;
			if(i== 24) {
				incident=issueReportedList
						.stream()
						.filter(item ->
								(item.getTime().isAfter(ZonedDateTime.of(finalDate.withSecond(0).withMinute(0).withNano(0),
										ZoneId.systemDefault()).toInstant())
										&&
										item.getTime().isBefore(ZonedDateTime.of(finalDate,
												ZoneId.systemDefault()).toInstant())))
						.count();
			}else{
				incident = issueReportedList
						.stream()
						.filter(item ->
								(item.getTime().isAfter(ZonedDateTime.of(finalDate,
										ZoneId.systemDefault()).toInstant())
										&&
										item.getTime().isBefore(ZonedDateTime.of(finalDate.plusHours(1),
												ZoneId.systemDefault()).toInstant())))
						.count();
			}
			report.add(Pair.of(date.withSecond(0).withMinute(0).withNano(0), incident));
			date = date.minusHours(1).withSecond(0).withMinute(0).withNano(0);
		}
		return report;
	}

	@RequestMapping(path = "/reportIssue", method = RequestMethod.POST)
	public boolean reportIssue(@RequestBody ReportIssueRequest uuid) {
		//TODO: check if uuid already reported problem in the last hour to avoid spam
		Point p = Point.measurement("issue_reported").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
				.addField("issue_uuid", uuid.getUuid())
				.addField("time", System.currentTimeMillis())
				.build();
		BlablamovebackendApplication.influxDB.write(p);
		System.out.println("User " + uuid + " reported an issue");
		return true;
	}

	@RequestMapping(path = "/lastUpdate", method = RequestMethod.GET)
	public LocalDateTime getLastUpdate() {
		Query queryObject = new Query("Select * from issue_reported ORDER BY time DESC LIMIT 1", "blablamove");
		QueryResult queryResult = BlablamovebackendApplication.influxDB.query(queryObject);
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		List<IssueReported> issueReportedList = resultMapper
				.toPOJO(queryResult, IssueReported.class);
		if (issueReportedList.size() == 1) {
			return LocalDateTime.ofInstant(issueReportedList.get(0).getTime(),ZoneId.systemDefault());
		}
		return LocalDateTime.now();
	}
}
