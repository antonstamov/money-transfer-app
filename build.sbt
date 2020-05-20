
name := """money-transfer-app"""

version := "1.0-SNAPSHOT"

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.11"

val akkaHttpV = "10.0.11"
val slickV = "3.2.1"
val h2V = "1.4.196"
val circeV = "0.9.1"
val scalaTestV = "3.0.4"

libraryDependencies ++= Seq(
  //akka-http
  // HTTP server
  "com.typesafe.akka" %% "akka-http" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-core" % akkaHttpV,

  // SQL generator
  "com.typesafe.slick" %% "slick" % slickV,
  "com.typesafe.slick" %% "slick-codegen" % slickV,

  // Migration for SQL databases
  "org.flywaydb" % "flyway-core" % "4.2.0",

  // Connection pool for database
  "com.zaxxer" % "HikariCP" % "2.7.0",
  // H2
  "com.h2database" % "h2" % h2V,


  // JSON serialization library
  "io.circe" %% "circe-core" % circeV,
  "io.circe" %% "circe-generic" % circeV,
  "io.circe" %% "circe-parser" % circeV,
  // Sugar for serialization and deserialization in akka-http with circe
  "de.heikoseeberger" %% "akka-http-circe" % "1.19.0",

  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",

  "org.scalatest" %% "scalatest" % scalaTestV % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV % "test"

)

// codegen task
//sourceGenerators in Compile += slick.taskValue // Automatic code generation on build
//
//lazy val slick = taskKey[Seq[File]]("Generate Tables.scala")
//slick := {
//  val dir = (sourceManaged in Compile) value
//  val outputDir = dir / "slick"
//  val url = "jdbc:h2:./db"//;INIT=runscript from 'src/main/resources/db/migration/V1__tables.sql'" // connection info
//  val jdbcDriver = "org.h2.Driver"
//  val slickDriver = "slick.jdbc.H2Profile"
//  val pkg = "transferapp"
//
//  val cp = (dependencyClasspath in Compile) value
//  val s = streams value
//
//  runner.value.run("slick.codegen.SourceCodeGenerator",
//    cp.files,
//    Array(slickDriver, jdbcDriver, url, outputDir.getPath, pkg),
//    s.log).failed foreach (sys error _.getMessage)
//
//  val file = outputDir / pkg / "Tables.scala"
//
//  Seq(file)
//}
