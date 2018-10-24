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
    scn.inject(atOnceUsers(1)) // 12
  ).protocols(httpProtocol) // 13
}