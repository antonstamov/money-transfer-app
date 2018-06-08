package repo

import restapi.utils.db.DatabaseService
import models.db.{Tables, TransferRow, WalletRow}

import scala.concurrent.{ExecutionContext, Future}

class TransferRepository(val databaseService: DatabaseService)(implicit ex: ExecutionContext)
  extends Tables {

  import databaseService._
  import databaseService.profile.api._

  val profile = databaseService.profile


  def findById(id: Int): Future[Option[TransferRow]] = db.run(Transfer.filter(_.id === id).result.headOption)

  def findAll(): Future[Seq[TransferRow]] = db.run(Transfer.result)

  def delete(id: Int): Future[Int] = db.run(Transfer.filter(_.id === id).delete)

  def makeTransfer(senderWallet: WalletRow, receiverWallet: WalletRow, amount: Double): Future[Unit] = {
    val updateSender = Wallet.filter(_.id === senderWallet.id).update(senderWallet.copy(amount = senderWallet.amount - amount))
    val updateReceiver = Wallet.filter(_.id === receiverWallet.id).update(receiverWallet.copy(amount = receiverWallet.amount + amount))
    val createTransfer = Transfer += TransferRow(0, senderWallet.userId, receiverWallet.userId, amount)
    db.run(DBIO.seq(updateSender, updateReceiver, createTransfer).transactionally)
  }
}
