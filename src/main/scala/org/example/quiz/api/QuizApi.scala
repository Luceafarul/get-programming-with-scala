package org.example.quiz.api

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.EntityEncoder
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import org.example.quiz.service.QuizService
import org.example.quiz.dao.records.Quiz
import org.example.quiz.dao.codecs.QuizJson._

class QuizApi(quizService: QuizService) extends Http4sDsl[IO] {
  private implicit val quizEntityEncoder: EntityEncoder[IO, Quiz] = jsonEncoderOf[IO, Quiz]

  private object CategoryParam extends QueryParamDecoderMatcher[Long]("category_id")

  val routes = HttpRoutes.of[IO] { case GET -> Root :? CategoryParam(categoryId) =>
    quizService.generate(categoryId).flatMap {
      case Some(quiz) => Ok(quiz)
      case None       => NotFound(s"Category $categoryId not found")
    }
  }
}
