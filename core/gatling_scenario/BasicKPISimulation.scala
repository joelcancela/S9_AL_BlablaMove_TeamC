package computerdatabase

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicKPISimulation extends Simulation { 

  val userHttpProtocol = http 
    .baseUrl("http://127.0.0.1:5001")

  val askingForKPI = scenario("AskingForKPI").exec(http("ask")
      .post("/kpi/city/top10")
      )

  setUp( 
    usersRegistering.inject(
      heavisideUsers(10) during (10 seconds)
    )
  ).protocols(userHttpProtocol)
}