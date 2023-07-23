package org.example.quiz.dao.domain

final case class Score(score: Double, correct: List[GivenAnswer], wrong: List[GivenAnswer])
