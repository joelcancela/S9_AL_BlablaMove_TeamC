package fr.polytech.unice.blablamove.teamc.blablamovebackend.webservice;

import fr.polytech.unice.blablamove.teamc.blablamovebackend.model.ConnectionLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
		for (int i = 0; i < 24 ; i++) {
			if(LocalDateTime.now().minusHours(i).getHour() <= 13 && LocalDateTime.now().minusHours(i).getHour() >= 11){//Simuler pic entre 11h et 13h
				connections.add(new ConnectionLog(LocalDateTime.now().minusHours(i),rand.nextInt(2000)+1500));
			}else{
				connections.add(new ConnectionLog(LocalDateTime.now().minusHours(i),rand.nextInt(2000)));
			}
		}
	}


	@RequestMapping(path = "/last24Connections",method =  RequestMethod.GET)
	public List<ConnectionLog> getLast24Connections() {
		return connections;
	}
}
