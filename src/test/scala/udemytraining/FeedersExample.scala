package udemytraining

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class FeedersExample extends Simulation{
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  val csvFeeder = csv("data/dataStudent.csv").circular

  def getSpecificStudent():ChainBuilder = {
    repeat(10) {
      feed(csvFeeder)
        .exec(http("Get specific student")
        .get("/students/${columnId}")
        .check(jsonPath("$.name")
          .is("${studentName}"))
        .check(status.is(200)))
    }
  }

  val scn = scenario("Get specific student")
      .exec(getSpecificStudent())

  setUp(scn.inject(atOnceUsers(2))
    .protocols(httpConfig))
}
