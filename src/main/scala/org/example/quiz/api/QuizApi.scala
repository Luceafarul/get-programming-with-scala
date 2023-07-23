package org.example.quiz.api

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.EntityEncoder
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import org.example.quiz.service.QuizService
import org.example.quiz.dao.domain.Quiz
import org.example.quiz.dao.formats.QuizJson._
import org.example.quiz.dao.formats.ScoreJson._
import org.example.quiz.dao.formats.GivenAnswerJson._
import org.http4s.dsl.request
import org.example.quiz.dao.domain.GivenAnswer
import org.example.quiz.dao.domain.Score
import org.http4s.EntityDecoder

class QuizApi(quizService: QuizService) extends Http4sDsl[IO] {
  private implicit val quizEntityEncoder: EntityEncoder[IO, Quiz]                     = jsonEncoderOf[IO, Quiz]
  private implicit val scoreEntityEncoder: EntityEncoder[IO, Score]                   = jsonEncoderOf[IO, Score]
  private implicit val givenAnswersEntityDecoer: EntityDecoder[IO, List[GivenAnswer]] = jsonOf[IO, List[GivenAnswer]]

  private object CategoryParam extends QueryParamDecoderMatcher[Long]("category_id")

  val routes = HttpRoutes.of[IO] {
    case GET -> Root :? CategoryParam(categoryId) =>
      quizService.generate(categoryId).flatMap {
        case Some(quiz) => Ok(quiz)
        case None       => NotFound(s"Category $categoryId not found")
      }
    case request @ POST -> Root =>
      val response =
        for {
          answers <- request.as[List[GivenAnswer]]
          score   <- quizService.score(answers)
        } yield score
      Ok(response)
  }
}
