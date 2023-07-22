package org.example.quiz.dao.records

final case class Answer(id: Long = 0, questionId: Long = 0, text: String, isCorrect: Boolean = false)
