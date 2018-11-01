package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.City;
import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.CityReport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/marketing")
public class MarketingWS {

    private List<City> cities = new ArrayList<>();
    private List<CityReport> citiesReports = new ArrayList<>();

    //TODO: get data from DB
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

        citiesReports.add(new CityReport(marseille,3528));
        citiesReports.add(new CityReport(antibes, 1352));
        citiesReports.add(new CityReport(toulon, 931));
        citiesReports.add(new CityReport(aix, 2500));
        citiesReports.add(new CityReport(nice, 2955));
    }

    @RequestMapping(path = "/cities",method =  RequestMethod.GET)
    public List<City> getAllActiveCities() {
        return cities;
    }

    @RequestMapping(path = "/mostActiveCities",method =  RequestMethod.GET)
    public List<CityReport> getMostActiveCitiesAllTime() {
        return citiesReports.stream().sorted((f1,f2) -> Integer.compare(f2.getTransactionCount(),
                f1.getTransactionCount())).collect(Collectors.toCollection(LinkedList::new));
    }
}