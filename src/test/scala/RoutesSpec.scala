import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import models.db.{UserRow, WalletRow}
import org.scalatest.{BeforeAndAfter, FlatSpec, MustMatchers}
import restapi.utils.db.{DatabaseMigrationManager, DatabaseService}
import routes.{AddFundsRequest, CreateTransferRequest, CreateUserRequest, HttpRoute}
import utils.Conf
import io.circe.generic.auto._
import com.typesafe.scalalogging.Logger


class RoutesSpec extends FlatSpec with BeforeAndAfter with ScalatestRouteTest with MustMatchers with FailFastCirceSupport with Conf {

  val logger = Logger(this.getClass)
  val databaseMigrationManager = new DatabaseMigrationManager(jdbcUrl, dbUser, dbPassword)
  databaseMigrationManager.migrateDatabaseSchema()
  val databaseService = new DatabaseService(jdbcUrl, dbUser, dbPassword)
  val routes = new HttpRoute(databaseService).route

  var senderId = 0
  var receiverId = 0
  it should "add a user with name 'Sender'" in {
    Post("/users", CreateUserRequest("Sender")) ~> routes ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[UserRow]
      res.name must equal("Sender")
      senderId = res.id

      logger.info(s"Created user: $res")
    }
  }

  it should "add a user with name 'Receiver'" in {
    Post("/users", CreateUserRequest("Receiver")) ~> routes ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[UserRow]
      res.name must equal("Receiver")
      receiverId = res.id
      logger.info(s"Created user: $res")
    }
  }

  "Sender" should "have an empty wallet" in {
    Get(s"/users/$senderId/wallet") ~> routes ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[WalletRow]
      res.amount must equal(0.0)
      logger.info(s"Sender wallet: $res")
    }
  }

  "Receiver" should "have an empty wallet" in {
    Get(s"/users/$receiverId/wallet") ~> routes ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[WalletRow]
      res.amount must equal(0.0)
      logger.info(s"Receiver wallet: $res")
    }
  }

  it should "add $3.5 to Sender wallet" in {
    Put(s"/users/$senderId/wallet/add_funds", AddFundsRequest(3.5)) ~> routes ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[WalletRow]
      res.amount must equal(3.5)
      logger.info(s"Sender wallet: $res")
    }
  }

  it should "send $2.5 from Sender to Receiver" in {
    Post(s"/transfers", CreateTransferRequest(senderId, receiverId, 2.5)) ~> routes ~> check {
      status must equal(StatusCodes.OK)
    }
  }

  "Sender" should "have $1 on his wallet" in {
    Get(s"/users/$senderId/wallet") ~> routes ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[WalletRow]
      res.amount must equal(1.0)
      logger.info(s"Sender wallet: $res")
    }
  }

  "Receiver" should "have $2.5 on his wallet" in {
    Get(s"/users/$receiverId/wallet") ~> routes ~> check {
      status must equal(StatusCodes.OK)
      val res = responseAs[WalletRow]
      res.amount must equal(2.5)
      logger.info(s"Sender wallet: $res")
    }
  }
}
