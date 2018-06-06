package restapi.utils.db

import org.flywaydb.core.Flyway

class DatabaseMigrationManager(jdbcUrl: String, dbUser: String, dbPassword: String) {

  private val flyway = new Flyway()
  flyway.setDataSource(jdbcUrl, dbUser, dbPassword)

  private var placeholderReplacement = false
  def  isPlaceholderReplacement(): Boolean = placeholderReplacement
  def setPlaceholderReplacement(placeholder: Boolean)= placeholderReplacement = placeholder
  flyway.setPlaceholderReplacement(false)

  def migrateDatabaseSchema() : Unit = flyway.migrate()

  def dropDatabase() : Unit = flyway.clean()

}
