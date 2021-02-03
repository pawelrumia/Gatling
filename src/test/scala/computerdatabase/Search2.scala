package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Search2 {
  val search2 = exec(http("GET")
    .get("/students")
    .check(status.is(200)))
    .pause(2)
    .exec(http("GET with id 4")
      .get("/students/4")
      .check(status.is(200)))
    .exec(http("GET with id z pliku")
      .get("/students/"))
    .feed(csv("ids.csv").queue)
    .exec(http("Search z pliku")
      .get("/students/${idjeden}")
      .check(status.is(200)))
}
