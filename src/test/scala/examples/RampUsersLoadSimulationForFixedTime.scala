package examples

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class RampUsersLoadSimulationForFixedTime extends Simulation {
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  def getAllStudents(): ChainBuilder = {
    exec(http("Get all students")
      .get("/students")
      .check(status.is(200)))
  }

  def getSpecificStudent(): ChainBuilder = {
    repeat(3) {
      exec(http("Get student with specific id")
        .get("/students/3")
        .check(status.is(200)))
    }
  }

  val scen = scenario("Basic Load Test")
    .forever() {
      exec(getAllStudents())
        .pause(2)
        .exec(getSpecificStudent())
        .pause(1)
    }
  /*running test for a fixed amount of otime */
  setUp(
    scen.inject(
      nothingFor(5 seconds),
      atOnceUsers(10),
      rampUsers(50) during (30 seconds))
      .protocols(httpConfig.inferHtmlResources()))
    .maxDuration(1 minute)
}