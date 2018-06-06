package routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import dto.MessageResponse
import io.circe.generic.auto._
import models.db.TransferRow
import repo.{TransferRepository, WalletRepository}
import restapi.utils.db.DatabaseService

import scala.concurrent.ExecutionContext

class TransferRoute(val databaseService: DatabaseService)(implicit ex: ExecutionContext)
  extends Directives with FailFastCirceSupport {
  val transferRepository = new TransferRepository(databaseService)
  val walletRepository = new WalletRepository(databaseService)

  val route =
    pathPrefix("transfers") {
      pathEndOrSingleSlash {
        post {
          entity(as[CreateTransferRequest]) { req: CreateTransferRequest =>
            onSuccess(for {
              senderWallet <- walletRepository.findByUserId(req.senderId)
              receiverWallet <- walletRepository.findByUserId(req.receiverId)
            } yield (senderWallet, receiverWallet)) {
              (senderWallet, receiverWallet) => senderWallet match {
                case Some(senderWallet) => receiverWallet match {
                  case Some(receiverWallet) =>
                    if (senderWallet.amount < req.amount) complete(StatusCodes.BadRequest -> MessageResponse(s"Sender don't have enough amount of money on his wallet to perform this transfer id"))
                    else {
                      complete(transferRepository.makeTransfer(senderWallet, receiverWallet, req.amount))
                    }
                  case None => complete(StatusCodes.BadRequest -> MessageResponse(s"Receiver with id = ${req.receiverId} not found"))
                }
                case None => complete(StatusCodes.BadRequest -> MessageResponse(s"Sender with id = ${req.senderId} not found"))
              }
            }
          }
        } ~
          get {
            complete(OK -> transferRepository.findAll())
          }
      } ~
        path(IntNumber) { id =>
          get {
            complete(OK -> transferRepository.findById(id))
          } ~
            delete {
              complete(OK -> transferRepository.delete(id))
            }
        }
    }
}

case class CreateTransferRequest(senderId: Int, receiverId: Int, amount: Double) {
  def toTransfer = TransferRow(0, senderId, receiverId,amount)
}