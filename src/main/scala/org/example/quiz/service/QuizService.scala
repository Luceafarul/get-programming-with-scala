package org.example.quiz.service

import org.example.quiz.dao.QuestionAnswerDao
import org.example.quiz.dao.records.Quiz
import org.example.quiz.dao.records.Quiz.QuizQuestion
import cats.effect.ContextShift
import cats.effect.IO
import org.example.quiz.dao.records.Category
import org.example.quiz.dao.records.Question

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

  private def pickQuestions(category: Category, n: Int): IO[List[QuizQuestion]] = {
    val randomQuestions = IO.fromFuture(IO(dao.pickByCategoryId(category.id, n)))

    randomQuestions.map { questions =>
      questions.map { case (question, answers) =>
        QuizQuestion(question.id, question.text, answers.toList)
      }.toList
    }
  }
}
