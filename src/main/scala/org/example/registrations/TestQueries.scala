package org.example.registrations

import io.getquill._
import scala.concurrent.{ ExecutionContext, Future }

class TestQueries(ctx: PostgresJAsyncContext[SnakeCase.type]):
  import ctx._

  def testConnection()(implicit ec: ExecutionContext): Future[Boolean] =
    inline def q = quote { infix"SELECT 1".as[Int] }
    val result   = run(q)
    result.map(_ == 1)
