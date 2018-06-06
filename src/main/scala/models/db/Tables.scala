package models.db
// AUTO-GENERATED Slick data model

/** Entity class storing rows of table User
  *
  * @param id   Database column id SqlType(INTEGER), AutoInc, PrimaryKey
  * @param name Database column name SqlType(VARCHAR) */
case class UserRow(id: Int, name: String)

/** Entity class storing rows of table Wallet
  *  @param id Database column id SqlType(INTEGER), AutoInc, PrimaryKey
  *  @param userId Database column user_id SqlType(INTEGER)
  *  @param amount Database column amount SqlType(DOUBLE) */
case class WalletRow(id: Int, userId: Int, amount: Double)

/** Entity class storing rows of table Transfer
  *
  * @param id         Database column id SqlType(INTEGER), AutoInc, PrimaryKey
  * @param senderId   Database column sender_id SqlType(INTEGER)
  * @param receiverId Database column receiver_id SqlType(INTEGER)
  * @param amount     Database column amount SqlType(DOUBLE) */
case class TransferRow(id: Int, senderId: Int, receiverId: Int, amount: Double)


/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Transfer.schema ++ User.schema ++ Wallet.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema


  /** GetResult implicit for fetching TransferRow objects using plain SQL queries */
  implicit def GetResultTransferRow(implicit e0: GR[Int], e1: GR[Double]): GR[TransferRow] = GR{
    prs => import prs._
      TransferRow.tupled((<<[Int], <<[Int], <<[Int], <<[Double]))
  }
  /** Table description of table transfer. Objects of this class serve as prototypes for rows in queries. */
  class Transfer(_tableTag: Tag) extends profile.api.Table[TransferRow](_tableTag, "transfer") {
    def * = (id, senderId, receiverId, amount) <> (TransferRow.tupled, TransferRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(senderId), Rep.Some(receiverId), Rep.Some(amount)).shaped.<>({r=>import r._; _1.map(_=> TransferRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column sender_id SqlType(INTEGER) */
    val senderId: Rep[Int] = column[Int]("sender_id")
    /** Database column receiver_id SqlType(INTEGER) */
    val receiverId: Rep[Int] = column[Int]("receiver_id")
    /** Database column amount SqlType(DOUBLE) */
    val amount: Rep[Double] = column[Double]("amount")

    /** Foreign key referencing User (database name CONSTRAINT_4C) */
    lazy val userFk1 = foreignKey("CONSTRAINT_4C", senderId, User)(r => r.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    /** Foreign key referencing User (database name CONSTRAINT_4C5) */
    lazy val userFk2 = foreignKey("CONSTRAINT_4C5", receiverId, User)(r => r.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Transfer */
  lazy val Transfer = new TableQuery(tag => new Transfer(tag))


  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String]): GR[UserRow] = GR{
    prs => import prs._
      UserRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, "user") {
    def * = (id, name) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR) */
    val name: Rep[String] = column[String]("name")
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))

  /** GetResult implicit for fetching WalletRow objects using plain SQL queries */
  implicit def GetResultWalletRow(implicit e0: GR[Int], e1: GR[Double]): GR[WalletRow] = GR{
    prs => import prs._
      WalletRow.tupled((<<[Int], <<[Int], <<[Double]))
  }
  /** Table description of table wallet. Objects of this class serve as prototypes for rows in queries. */
  class Wallet(_tableTag: Tag) extends profile.api.Table[WalletRow](_tableTag, "wallet") {
    def * = (id, userId, amount) <> (WalletRow.tupled, WalletRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(userId), Rep.Some(amount)).shaped.<>({r=>import r._; _1.map(_=> WalletRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(INTEGER) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column amount SqlType(DOUBLE) */
    val amount: Rep[Double] = column[Double]("amount")

    /** Foreign key referencing User (database name CONSTRAINT_D0) */
    lazy val userFk = foreignKey("CONSTRAINT_D0", userId, User)(r => r.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Wallet */
  lazy val Wallet = new TableQuery(tag => new Wallet(tag))
}
