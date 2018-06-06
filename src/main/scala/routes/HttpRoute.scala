package routes

import akka.http.scaladsl.server.Directives
import restapi.utils.db.DatabaseService

import scala.concurrent.ExecutionContext

class HttpRoute(val databaseService: DatabaseService)(implicit ex: ExecutionContext)
  extends Directives {
  val userRoute = new UserRoute(databaseService)
  val transferRoute = new TransferRoute(databaseService)

  val route =
    userRoute.route ~
      transferRoute.route
}
