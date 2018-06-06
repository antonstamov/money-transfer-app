package utils

import com.typesafe.config.ConfigFactory

trait Conf {

  val config = ConfigFactory.load()
  private val databaseConfig = config.getConfig("database")

  val jdbcUrl = databaseConfig.getString("jdbc-url")
  val dbUser = databaseConfig.getString("username")
  val dbPassword = databaseConfig.getString("password")

  val httpHost = config.getString("http.host")
  val httpPort = config.getInt("http.port")
}