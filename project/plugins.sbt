// The Play plugin
resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("io.spray" %% "sbt-revolver" % "0.9.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")