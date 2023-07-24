package org.example.quiz

import org.example.quiz.dao.Daos
import org.example.quiz.api.Api
import scala.concurrent.ExecutionContext
import org.example.quiz.service.Services
import cats.effect.IOApp
import cats.effect.{ ExitCode, IO }
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.blaze.server.BlazeServerBuilder
import scala.concurrent.ExecutionContextExecutor

object QuizApp extends IOApp {
  private implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  private val daos     = new Daos(TestDatabase.ctx)
  private val services = new Services(daos)
  private val api      = new Api(services)

  private val httpApp = Router(
    "/" -> api.generic.routes,
    "categories" -> api.category.routes,
    "quiz" -> api.quiz.routes,
  ).orNotFound

  private def stream(args: List[String]) =
    BlazeServerBuilder[IO]
      .bindHttp(8000, "0.0.0.0")
      .withHttpApp(httpApp)
      .serve

  def run(args: List[String]): IO[ExitCode] =
    stream(args).compile.drain.as(ExitCode.Success)
}
