package udemytraining

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RampUsersLoadSimulation extends Simulation {
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
    .exec(getAllStudents())
    .pause(2)
    .exec(getSpecificStudent())
    .pause(1)

  /*running test for a fixed number of users */
  setUp(scen.inject(nothingFor(5 seconds),
    constantUsersPerSec(10) during (30 seconds))
    .protocols(httpConfig.inferHtmlResources()))
}