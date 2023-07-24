package org.example.quiz.dao

import io.getquill.PostgresJAsyncContext
import io.getquill.SnakeCase
import scala.concurrent.ExecutionContext

class Daos(ctx: PostgresJAsyncContext[SnakeCase.type])(implicit ec: ExecutionContext) {
  val generic        = new GenericDao(ctx)
  val category       = new CategoryDao(ctx)
  val questionAnswer = new QuestionAnswerDao(ctx)
}
