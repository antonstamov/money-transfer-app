package routes

import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import repo.{UserRepository, WalletRepository}
import restapi.utils.db.DatabaseService
import io.circe.generic.auto._
import models.db.{UserRow, WalletRow}

import scala.concurrent.ExecutionContext

class UserRoute(val databaseService: DatabaseService)(implicit ex: ExecutionContext)
  extends Directives with FailFastCirceSupport {
  val userRepository = new UserRepository(databaseService)
  val walletRepository = new WalletRepository(databaseService)
  val route =
    pathPrefix("users") {
      pathEndOrSingleSlash {
        post {
          entity(as[CreateUserRequest]) { req: CreateUserRequest =>
            onSuccess(
              for {
                user <- userRepository.create(req.toUser)
                _ <- walletRepository.create(WalletRow(0, user.id, 0))
              } yield user
            )(user => complete(OK -> user))
          }
        } ~
          get {
            complete(OK -> userRepository.findAll())
          }
      } ~
        pathPrefix(IntNumber) { id =>
          pathEndOrSingleSlash {
            get {
              complete(OK -> userRepository.findById(id))
            } ~
              put {
                entity(as[UpdateUserRequest]) { req: UpdateUserRequest =>
                  complete(OK -> userRepository.update(id, req.toUser(id)))
                }
              } ~
              delete {
                complete(OK -> userRepository.delete(id))
              }
          } ~
            pathPrefix("wallet") {
              get {
                complete(OK -> walletRepository.findByUserId(id))
              } ~
              pathPrefix("add_funds") {
                put {
                  for {

                  }
                  complete()
                }
              }
            }
        }
    }
}

case class CreateUserRequest(name: String) {
  def toUser = UserRow(0, name)
}

case class UpdateUserRequest(name: String) {
  def toUser(id: Int) = UserRow(id, name)
}