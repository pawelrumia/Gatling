package udemytraining

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.util.Random

class MoreAdvancedFeeder extends Simulation {
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")

  var idNumber = (5 to 20).iterator
  val rnd = new Random()
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val customFeeder = Iterator.continually(Map(
    "id" -> idNumber.next(),
    "name" -> ("Post" + randomString(3)),
    "course" -> ("Post" + randomString(3))
  ))

  def postNewStudent(): ChainBuilder = {
    repeat(5) {
      feed(customFeeder)
        .exec(http("Try to post a new student")
          .post("/students")
          .body(ElFileBody("models/basicStudentModel.json")).asJson
          .check(status.is(200)))
    }
  }

  val scn = scenario("Post new student")
    .exec(postNewStudent())

  setUp(scn.inject(atOnceUsers(1))
    .protocols(httpConfig))
}
