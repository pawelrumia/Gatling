package computerdatabase

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class AnotherSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder = http.baseUrl("http://localhost:8000")

  val scn2: ScenarioBuilder = scenario("Moj drugi test ")
    .exec(http("Get city")
      .get("/students/1")
      .check(status.is(200))
      .check(jsonPath("$.adres.miasto").is("Krakow")))
    .pause(2)


  setUp(scn2.inject(atOnceUsers(2))).protocols(httpConf)
    .assertions(global.successfulRequests.percent.gt(95))
    .assertions(global.responseTime.max.lt(50))
}
