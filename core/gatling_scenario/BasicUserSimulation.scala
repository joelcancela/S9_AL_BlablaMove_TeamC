package computerdatabase

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicUserSimulation extends Simulation { 

  val userHttpProtocol = http 
    .baseUrl("http://127.0.0.1:5000")

  //val usersRegistering = scenario("UsersRegistering").exec(User.register)
  val usersLoginAndLogout = scenario("UsersLoginAndLogout").exec(User.loginAndLogout)

  setUp( 
   // usersRegistering.inject(
    //  rampUsersPerSec(10) to 200 during (10 seconds) randomized,
    //  heavisideUsers(1000) during (10 seconds)
   // ),
    usersLoginAndLogout.inject(
      rampUsersPerSec(10) to 15 during (25 seconds) randomized,
    )
  ).protocols(userHttpProtocol)
}

object User {
  //val register = exec(http("Register")
  //    .post("/user/register")
  //    )

  val loginAndLogout = exec(http("Login")
      .post("/user/login")
      )
    .pause(1, 5)
    .exec(http("Logout")
      .post("/user/logout")
      )
}