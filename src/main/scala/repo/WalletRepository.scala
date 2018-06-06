package repo

import models.db.{Tables, WalletRow}
import restapi.utils.db.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class WalletRepository(val databaseService: DatabaseService)(implicit ex: ExecutionContext)
  extends Tables {
  import databaseService._
  import databaseService.profile.api._
  val profile = databaseService.profile


  def create(wallet: WalletRow): Future[Int] = db.run(Wallet += wallet)
  def findByUserId(userId: Int): Future[Option[WalletRow]] = db.run(Wallet.filter(_.userId === userId).result.headOption)
  def updateByUserId(userId: Int, wallet: WalletRow) = db.run(Wallet.filter(_.userId === userId).update(wallet)).map{case 0 => None case _ => Some(wallet)}

}