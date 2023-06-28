package org.example.registrations

import io.getquill._
import java.time.LocalDate
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class ProductQueries(ctx: PostgresJAsyncContext[SnakeCase.type]):
  import ctx._
  import ProductQueries._

  private val products = quote { query[Product] }

  // Create a product.
  def save(product: Product)(implicit ec: ExecutionContext): Future[Int] =
    val q = quote { products.insertValue(lift(product)).returning(_.id) }
    run(q)

  // Select all of those with a given title.
  def productByTitle(title: String)(implicit ec: ExecutionContext): Future[Seq[Product]] =
    val q = quote { products.filter(_.title == lift(title)) }
    run(q)

  // Change the title of a specific product.
  def updateTitleById(id: Int, newTitle: String)(implicit ec: ExecutionContext): Future[Long] =
    val q = quote { products.filter(_.id == lift(id)).update(_.title -> lift(newTitle)) }
    run(q)

  // Delete a product by ID.
  def deleteById(id: Int)(implicit ec: ExecutionContext): Future[Long] =
    val q = quote { products.filter(_.id == lift(id)).delete }
    run(q)

object ProductQueries:
  final case class Product(id: Int, title: String, creationDate: LocalDate)
