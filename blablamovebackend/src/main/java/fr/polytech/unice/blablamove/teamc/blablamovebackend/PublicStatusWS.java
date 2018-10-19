package fr.polytech.unice.blablamove.teamc.blablamovebackend;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
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
            date = date.minusHours(1);
            Random rand = new Random();
            int incident = rand.nextInt(20);
            report.add(Pair.of(date, incident));
        }
        return report;
    }
}
