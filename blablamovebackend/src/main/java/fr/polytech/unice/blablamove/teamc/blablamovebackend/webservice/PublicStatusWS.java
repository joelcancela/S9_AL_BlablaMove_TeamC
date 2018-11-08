package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.dto.ReportIssueRequest;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "/publicstatus")
public class PublicStatusWS {

    @RequestMapping(path = "/last24hIncidents")
    public List<Pair<LocalDateTime,Integer>> getLast24hIncidents() {
        List<Pair<LocalDateTime, Integer>> report = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now().withSecond(0).withMinute(0).withNano(0);
        for (int i = 24; i > 0; i--) {
            Random rand = new Random();
            int incident = rand.nextInt(20);
            report.add(Pair.of(date, incident));
            date = date.minusHours(1);
        }
        return report;
    }

    @RequestMapping(path ="/reportIssue", method = RequestMethod.POST)
    public boolean reportIssue(@RequestBody ReportIssueRequest uuid){
        System.out.println("User "+uuid+" reported an issue");
        //TODO: check uuid for spam + add issue to db
        return true;
    }

    @RequestMapping(path ="/lastUpdate", method = RequestMethod.GET)
    public LocalDateTime getLastUpdate(){
        //TODO: retrieve last update from db
        return LocalDateTime.now().minusMinutes(15);
    }
}
