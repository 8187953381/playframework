/*
 * Copyright (C) 2009-2019 Lightbend Inc. <https://www.lightbend.com>
 */

package play.core.server.akkahttp

import play.api.Configuration
import akka.http.scaladsl.model.headers.Host

private[server] final class AkkaServerConfigReader(akkaServerConfig: Configuration) {
  def getHostHeader: Either[Throwable, Host] = {
    Host
      .parseFromValueString(akkaServerConfig.get[String]("default-host-header"))
      .left
      .map { errors =>
        akkaServerConfig.reportError(
          "default-host-header",
          "Couldn't parse default host header",
          new RuntimeException(errors.map(_.formatPretty).mkString(", "))
        )
      }
  }
}
