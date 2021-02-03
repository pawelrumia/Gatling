package computerdatabase

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder = http.baseUrl("http://localhost:8000")

  val scn2: ScenarioBuilder =
    scenario("Checking")
      .exec(Search2.search2)

  //    val users: ScenarioBuilder = scenario("Users")
  setUp(scn2.inject(atOnceUsers(2))).protocols(httpConf)
    .assertions(global.successfulRequests.percent.gt(95))
    .assertions(global.responseTime.max.lt(50))

  object Task1 {
    def getPatryszka = exec(http("Get Patryszka")
      .get("/students")
      .check(jsonPath("$[*].name")
        .findAll.saveAs("Patryszka")))
  }

  //  object Search {
  //    val httpConf: HttpProtocolBuilder = http.baseUrl("http://localhost:8000")
  //    val feeder = csv("ids.csv").random
  //    val users: ScenarioBuilder = scenario("Users").exec(Search.search)
  //    val search = exec(http("GET")
  //      .get("/"))
  //      .pause(1)
  //      .feed(feeder) // 3
  //      .exec(http("GET")
  //      .get("/students")
  //      .check(status.is(200)))
  //      .exec(http("GET with id z pliku")
  //        .get("/students/"))
  //      .feed(csv("ids.csv").queue)
  //
  //    setUp(users.inject(atOnceUsers(10)).protocols(httpConf))
}

