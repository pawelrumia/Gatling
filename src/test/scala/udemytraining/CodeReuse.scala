package udemytraining

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class CodeReuse extends Simulation {
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  // 2) scenario details
  val scen = scenario("First basic test")
    .exec(http("Get all students first")
      .get("/students"))

    .exec(http("Get student with specific id")
      .get("/students/3"))

    .exec(http("Get student with specific id second time")
      .get("/students/3"))

  //new scenario
  val scenarioRewritten = scenario("Code reuse")
    .exec(getAllStudents())
    .pause(1)
    .exec(getSpecificStudent())

  //reuse methods
  def getAllStudents(): ChainBuilder = {
    exec(http("Get all students first")
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

  setUp(scenarioRewritten.inject(atOnceUsers(5)).protocols(httpConfig))
}
