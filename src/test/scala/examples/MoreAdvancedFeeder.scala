package examples


import java.time.format.DateTimeFormatter

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._

import scala.util.Random

class MoreAdvancedFeeder extends Simulation {
  val httpConfig = http.baseUrl("http://localhost:8000")
    .header("Accept", "application/json")
    .proxy(Proxy("localhost", 8000).httpsPort(8000))

  var idNumber = (40 to 45).iterator
  var randomIdNumber = (50 to 300).iterator
  val rnd = new Random()
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  val customFeeder = Iterator.continually(Map(
    "id" -> idNumber.next(),
    "name" -> ("Name " + randomString(3)),
    "course" -> ("Course" + randomString(3)),
    "city" -> ("City " + randomString(4)),
    "street" -> ("Street " + randomString(5)),
    "number" -> randomIdNumber.next(),
    "school" -> ("School name " + randomString(6)),
    "specialization" -> ("Specialization " + randomString(2)),
    "graduationYear" -> randomIdNumber.next()
  ))

  def postNewStudent(): ChainBuilder = {
    repeat(5)
    {
      feed(customFeeder)
        .exec(http("Try to post a new student")
          .post("/students")
          .body(ElFileBody("models/basicStudentModel.json")).asJson
          .check(status.is(200)))
        .exec { session => println(session); session }
    }
  }

  val scn = scenario("Post new student")
    .exec(postNewStudent())

  setUp(scn.inject(atOnceUsers(1))
    .protocols(httpConfig))
}
