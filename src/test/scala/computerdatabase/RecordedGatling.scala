package computerdatabase

class RecordedGatling {

  import io.gatling.core.Predef._
  import io.gatling.http.Predef._

  class RecordedGatling extends Simulation {

    val httpProtocol = http
      .baseUrl("http://localhost:8000")
      .inferHtmlResources()
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7")
      .upgradeInsecureRequestsHeader("1")
      .userAgentHeader("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")

    val headers_0 = Map(
      "Sec-Fetch-Dest" -> "document",
      "Sec-Fetch-Mode" -> "navigate",
      "Sec-Fetch-Site" -> "none",
      "Sec-Fetch-User" -> "?1")



    val scn = scenario("RecordedSimulation")
      .exec(http("request_0")
        .get("/students/")
        .headers(headers_0))

    setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
  }
}
