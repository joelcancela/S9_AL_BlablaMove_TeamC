package computerdatabase // 1

import io.gatling.core.Predef._ // 2
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PaymentSimulation extends Simulation { // 3

  val httpProtocol = http // 4
    .baseUrl("http://127.0.0.1:8080")

  val scn = scenario("PaymentSimulation") // 7
    .exec(http("request_1") // 8
      .post("/")) // 9
    .pause(5) // 10

  setUp( // 11
    scn.inject(
	    nothingFor(4 seconds), // 1
	    atOnceUsers(10), // 2
	    rampUsers(10) during (5 seconds), // 3
	    constantUsersPerSec(200) during (15 seconds), // 4
	    constantUsersPerSec(200) during (15 seconds) randomized, // 5
	    rampUsersPerSec(10) to 200 during (10 seconds), // 6
	    rampUsersPerSec(10) to 200 during (10 seconds) randomized, // 7
	    heavisideUsers(1000) during (20 seconds) // 8‡
    ) // 12
  )	.protocols(httpProtocol) // 13
}