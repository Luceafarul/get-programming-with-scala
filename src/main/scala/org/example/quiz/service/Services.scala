package org.example.quiz.service

import org.example.quiz.dao.Daos
import cats.effect.ContextShift
import cats.effect.IO

class Services(daos: Daos)(implicit cs: ContextShift[IO]) {
  val generic  = new GenericService(daos.generic)
  val category = new CategoryService(daos.category)
  val quiz     = new QuizService(daos.questionAnswer, category)
}
