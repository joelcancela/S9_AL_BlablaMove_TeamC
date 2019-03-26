package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoginLocalSimulation extends Simulation {
  val baseURL = http.baseURL("http://host.docker.internal:5001")
  val delivery = scenario("login simulation").exec(LoginSimulation.login)
  setUp(delivery.inject(rampUsersPerSec(20) to 200 during (20 seconds) randomized)).protocols(baseURL)
}

object LoginSimulation {
  val login = exec(http("Login")
                  .post("/user/login")
                  .body(StringBody("lol")))
                  .pause(3)
}