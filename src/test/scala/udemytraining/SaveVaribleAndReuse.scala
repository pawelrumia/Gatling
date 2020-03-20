package udemytraining

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class SaveVaribleAndReuse extends Simulation {
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  // 2) scenario details
  val scen = scenario("First basic test")
    .exec(http("Get all students")
      .get("/students")
      .check(jsonPath("$[3].id").saveAs("recordId")))
    .exec { session => println(session); session }

  exec(http("Get student with specific id")
    .get("/students/${recordId}")
    .check(jsonPath("$.adres.miasto")
      .is("lecimy"))
    .check(bodyString.saveAs("responseBody")))
    .exec { session => println(session("responseBody")
      .as[String]); session }

  setUp(scen.inject(atOnceUsers(5)).protocols(httpConfig))
}
