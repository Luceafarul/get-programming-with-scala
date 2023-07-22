package org.example.quiz.api

import org.example.quiz.service.CategoryService
import org.example.quiz.dao.records.Category
import org.example.quiz.dao.codecs.CategoryJson._
import org.http4s.dsl.Http4sDsl
import org.http4s.EntityEncoder
import org.http4s.HttpRoutes
import org.http4s.circe._
import cats.effect.IO

class CategoryApi(categoryService: CategoryService) extends Http4sDsl[IO] {
  private implicit val categoriesEncoder: EntityEncoder[IO, List[Category]] = jsonEncoderOf[IO, List[Category]]

  val routes = HttpRoutes.of[IO] { case GET -> Root =>
    Ok(categoryService.all())
  }
}
