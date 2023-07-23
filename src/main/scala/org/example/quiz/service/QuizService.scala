package org.example.quiz.service

import org.example.quiz.dao.QuestionAnswerDao
import cats.effect.ContextShift
import cats.effect.IO
import org.example.quiz.dao.domain.Quiz.QuizQuestion
import org.example.quiz.dao.domain.GivenAnswer
import org.example.quiz.dao.domain.Category
import org.example.quiz.dao.domain.Question
import org.example.quiz.dao.domain.Quiz
import org.example.quiz.dao.domain.Score

class QuizService(
  dao: QuestionAnswerDao,
  categoryService: CategoryService
)(implicit cs: ContextShift[IO]) {
  private val numberOfQuestions = 10

  def generate(categoryId: Long): IO[Option[Quiz]] =
    categoryService.get(categoryId).flatMap {
      case Some(category) =>
        pickQuestions(category, numberOfQuestions).map { qqs =>
          Some(Quiz(qqs))
        }
      case None => IO(None)
    }

  def score(givenAnswers: List[GivenAnswer]): IO[Score] = {
    val questionIds = givenAnswers.map(_.questionId)

    IO.fromFuture(IO(dao.getCorrectQuestionAnswers(questionIds)))
      .map { correctAnswers =>
        val goodAnswers = givenAnswers.filter { answer =>
          correctAnswers.exists { case (q, a) =>
            q == answer.questionId && a == answer.answerId
          }
        }

        val badAnswers = givenAnswers.diff(goodAnswers)

        val score = 1.0 * goodAnswers.size / givenAnswers.size
        Score(score, correct = goodAnswers, wrong = badAnswers)
      }
  }

  private def pickQuestions(category: Category, n: Int): IO[List[QuizQuestion]] = {
    val randomQuestions = IO.fromFuture(IO(dao.pickByCategoryId(category.id, n)))

    randomQuestions.map { questions =>
      questions.map { case (question, answers) =>
        QuizQuestion(question.id, question.text, answers.toList)
      }.toList
    }
  }
}
