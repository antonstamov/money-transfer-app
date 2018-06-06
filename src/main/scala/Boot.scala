import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import routes.HttpRoute
import restapi.utils.db.{DatabaseMigrationManager, DatabaseService}
import utils.Conf

import scala.concurrent.ExecutionContext

object Boot extends App with Conf {

  def startApplication() = {
    implicit val actorSystem = ActorSystem()
    implicit val executor: ExecutionContext = actorSystem.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val databaseMigrationManager = new DatabaseMigrationManager(jdbcUrl, dbUser, dbPassword)
    databaseMigrationManager.migrateDatabaseSchema()

    val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)

    val httpRoute = new HttpRoute(databaseService)

    Http().bindAndHandle(httpRoute.route, httpHost, httpPort)
  }

  startApplication()
}
