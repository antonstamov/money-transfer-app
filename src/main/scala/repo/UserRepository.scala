package repo

import models.db.{Tables, UserRow}
import restapi.utils.db.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class UserRepository(val databaseService: DatabaseService)(implicit ex: ExecutionContext)
  extends Tables {
  import databaseService._
  import databaseService.profile.api._
  val profile = databaseService.profile


  val insertQuery = User returning User.map(_.id) into ((user, id) => user.copy(id = id))


  def create(user: UserRow): Future[UserRow] = db.run(insertQuery += user)
  def findById(id: Int):Future[Option[UserRow]] = db.run(User.filter(_.id === id).result.headOption)
  def findAll():Future[Seq[UserRow]] = db.run(User.result)
  def update(id: Int, user: UserRow): Future[Option[UserRow]] = db.run(User.filter(_.id === id).update(user)).map{case 0 => None case _ => Some(user)}
  def delete(id: Int): Future[Int] = db.run(User.filter(_.id === id).delete)
}