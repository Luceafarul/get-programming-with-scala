package org.example.quiz.stubs.dao

import org.example.quiz.dao.domain.Category

object Fixtures {
  val categoryA = Category(id = 1L, name = "General")
  val categoryB = Category(id = 2L, name = "History")

  val categories = List(categoryA, categoryB)
}
