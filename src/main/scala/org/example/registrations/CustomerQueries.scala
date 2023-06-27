package org.example.registrations

import io.getquill._
import scala.concurrent.{ ExecutionContext, Future }

class CustomerQueries(ctx: PostgresJAsyncContext[SnakeCase.type]):
  import ctx._
  import CustomerQueries._

  private val customers = quote { query[Customer] }

  // Generated SQL: SELECT x.id, x.name FROM customer x
  def all()(implicit ec: ExecutionContext): Future[Seq[Customer]] =
    run(customers)

  // Generated SQL: SELECT x1.name FROM customer x1 WHERE x1.id = ?
  def nameById(id: Int)(implicit ec: ExecutionContext): Future[Seq[String]] =
    val q = quote { customers.filter(_.id == lift(id)).map(_.name) }
    run(q)

  // Generated SQL: INSERT INTO customer (id,name) VALUES (?, ?) RETURNING name
  def save(customer: Customer)(implicit ec: ExecutionContext): Future[String] =
    val q = quote { customers.insertValue(lift(customer)).returning(_.name) }
    run(q)

  // Generated SQL: UPDATE customer SET name = ? WHERE id = ?
  def updateNameById(id: Int, newName: String)(implicit ec: ExecutionContext): Future[Long] =
    val q = quote { customers.filter(_.id == lift(id)).update(_.name -> lift(newName)) }
    run(q)

  // Generated SQL: DELETE FROM customer WHERE id = ?
  def deleteById(id: Int)(implicit ec: ExecutionContext): Future[Long] =
    def q = quote { customers.filter(_.id == lift(id)).delete }
    run(q)

  // Generated SQL: SELECT x1.id, x1.name FROM customer x1 WHERE x1.name = ?
  def customerByName(name: String)(implicit ec: ExecutionContext): Future[Seq[Customer]] =
    val q = quote { customers.filter(_.name == lift(name)) }
    run(q)

object CustomerQueries:
  final case class Customer(id: Int, name: String)
