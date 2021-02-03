package examples

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

class CustomFeedersExample extends Simulation {
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  var idNumbers = (1 to 5).iterator
  val customFeeder = Iterator
    .continually(Map("studentId" -> idNumbers.next()))

  def getSpecificStudent(): ChainBuilder = {
    repeat(6) {
      feed(customFeeder)
        .exec(http("Get specific student")
          .get("/students/${studentId}")
          .check(status.is(200)))
    }
  }

  val scn = scenario("Get specific student")
    .exec(getSpecificStudent())

  setUp(scn.inject(atOnceUsers(2))
    .protocols(httpConfig))
}
