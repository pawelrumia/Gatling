package udemytraining

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class UdemyFirstScript extends Simulation {

  // 1) http config
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  // 2) scenario details
  val scn = scenario("First basic test")
    .exec(http("Get all students")
      .get("/students")
    .check(status.in(200 to 205)))
    .pause(1)

  // 3) load simulation config
  setUp(scn.inject(atOnceUsers(2))
    .protocols(httpConfig))

}
