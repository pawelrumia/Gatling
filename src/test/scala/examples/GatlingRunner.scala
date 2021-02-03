package examples

import io.gatling.app.Gatling
import io.gatling.core.config._

object GatlingRunner {
  def main(args: Array[String]): Unit = {
    val simulationClass = classOf[MoreAdvancedFeeder].getName

    val properties = new GatlingPropertiesBuilder
    properties.simulationClass(simulationClass)

    Gatling.fromMap(properties.build)
  }
}
