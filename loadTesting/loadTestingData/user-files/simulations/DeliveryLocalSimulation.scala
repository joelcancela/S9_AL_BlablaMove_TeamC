package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DeliveryLocalSimulation extends Simulation {
  val baseURL = http.baseURL("http://host.docker.internal:5002")
  val delivery = scenario("delivery cycle").exec(DeliverySimulation.delivery)
  setUp(delivery.inject(rampUsersPerSec(20) to 200 during (20 seconds) randomized)).protocols(baseURL)
}

object DeliverySimulation {
  val delivery = exec(http("Delivery creation")
                  .post("/delivery/route")
                  .body(StringBody("lol")))
                  .pause(1)
                  .exec(http("Delivery initialization")
                  .post("/delivery")
                  .body(StringBody("lol")))
                  .pause(1)
                  .randomSwitch(
                    60d -> exec(http("Delivery item")
                            .post("/delivery/item")
                            .body(StringBody("lol"))),
                    20d ->  exec(http("Delivery issue")
                            .post("/delivery/issue")
                            .body(StringBody("lol"))),
                    20d -> exec(http("Delivery canceled")
                            .delete("/delivery/route")
                            .body(StringBody("lol")))
                  )
}