package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class 400kSimulation extends Simulation {
  val baseURL = http.baseURL("http://www.nirousseau.ovh")
  val delivery = scenario("delivery cycle").exec(Simulations.delivery)
  setUp(delivery.inject(rampUsersPerSec(20) to 20000 during (20 seconds) randomized,)).protocols(baseURL)
}

object Simulations {

  val delivery = exec(http("Login")
                  .post("/user/login"))
                  .pause(3)
                  .exec(http("Delivery creation")
                  .post("/delivery/route"))
                  .pause(1)
                  .exec(http("Delivery initialization")
                  .post("/delivery"))
                  .pause(1)
                  .randomSwitch(
                    60d -> .exec(http("Delivery item")
                            .post("/delivery/item")),
                    20d ->   .exec(http("Delivery issue")
                            .post("/delivery/issue")),
                    20d -> .exec(http("Delivery canceled")
                            .delete("/delivery/route"))
                  )
}