package examples

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class RuntimeParameters extends Simulation {
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  def getAllStudents(): ChainBuilder = {
    exec(http("Get all students")
      .get("/students")
      .check(status.is(200)))
  }

  val scen = scenario("Runtime parameters")
      .forever() {
        exec(getAllStudents())
      }

  setUp(scen.inject(
    nothingFor(5 seconds),
    atOnceUsers(1000),
    rampUsers(30) during (1 second)))
    .protocols(httpConfig.inferHtmlResources())
    .maxDuration(30 seconds)
}

