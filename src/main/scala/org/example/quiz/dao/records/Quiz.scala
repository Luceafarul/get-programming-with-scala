package org.example.quiz.dao.records

import Quiz.QuizQuestion

final case class Quiz(questions: List[QuizQuestion])

object Quiz {
  final case class QuizQuestion(questionId: Long, questionText: String, possibleAnswer: List[Answer])
}
