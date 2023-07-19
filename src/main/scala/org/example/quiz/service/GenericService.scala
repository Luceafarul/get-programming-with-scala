package org.example.quiz.service

import org.example.quiz.dao.GenericDao
import cats.effect.ContextShift
import cats.effect.IO

class GenericService(dao: GenericDao)(implicit cs: ContextShift[IO]) {
  def healthCheck: IO[String] =
    checkDbConnection().map { success =>
      s"Database Connectivity: ${if (success) "OK" else "FAILURE"}"
    }

  private def checkDbConnection(): IO[Boolean] =
    IO.fromFuture(IO(dao.testConnection()))
      .handleErrorWith(_ => IO(false))
}
