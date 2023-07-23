package org.example.quiz.service

import org.example.quiz.dao.CategoryDao
import cats.effect.ContextShift
import cats.effect.IO
import org.example.quiz.dao.domain.Category

class CategoryService(dao: CategoryDao)(implicit cs: ContextShift[IO]) {
  def get(id: Long): IO[Option[Category]] =
    IO.fromFuture(IO(dao.findById(id)))

  def all(): IO[List[Category]] =
    IO.fromFuture(IO(dao.all())).map(_.toList)
}
