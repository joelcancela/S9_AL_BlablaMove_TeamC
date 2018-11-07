package computerdatabase

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicDeliverySimulation extends Simulation { 

  val userHttpProtocol = http 
    .baseUrl("http://127.0.0.1:5000")

  val deliveryCreation = scenario("deliveryCreation").exec(Delivery.createDelivery)
  val deliveryUpdate = scenario("deliveryUpdate").exec(Delivery.updateDelivery)

  setUp( 
    deliveryCreation.inject(
      rampUsersPerSec(10) to 200 during (10 seconds) randomized
    ),
    deliveryUpdate.inject(
      rampUsersPerSec(10) to 200 during (10 seconds) randomized,
      heavisideUsers(1000) during (10 seconds)
    )
  ).protocols(userHttpProtocol)
}

object Delivery {
  val createDelivery = exec(http("createDelivery")
      .post("/delivery")
      )

  val updateDelivery = exec(http("updateDelivery")
      .post("/delivery/checkpoint")
      )
    .pause(1, 5)
    .exec(http("updateDelivery")
      .post("/delivery/checkpoint")
      )
    .pause(1, 5)
    .exec(http("updateDelivery")
      .post("/delivery/checkpoint")
      )
}