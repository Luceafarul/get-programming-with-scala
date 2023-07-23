package org.example.quiz.dao.domain

import Quiz.QuizQuestion

final case class Quiz(questions: List[QuizQuestion])

object Quiz {
  final case class QuizQuestion(questionId: Long, questionText: String, possibleAnswer: List[Answer])
}
