package org.example.registrations

import io.getquill.PostgresJAsyncContext
import io.getquill.SnakeCase
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

class TestQueries(ctx: PostgresJAsyncContext[SnakeCase.type]) {
  import ctx._

  def testConnection()(implicit ec: ExecutionContext): Future[Boolean] = {
    val q      = quote { infix"SELECT 1".as[Int] }
    val result = run(q)
    result.map(_ == 1)
  }
}
