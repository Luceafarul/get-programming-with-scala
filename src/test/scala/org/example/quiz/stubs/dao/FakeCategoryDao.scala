package org.example.quiz.stubs.dao

import scala.concurrent.ExecutionContext
import org.example.quiz.dao.CategoryDao
import org.example.quiz.dao.domain.Category
import scala.concurrent.Future

class FakeCategoryDao(implicit ec: ExecutionContext) extends CategoryDao(ctx = null) {
  private var fakeCategories = Fixtures.categories

  private def safeModify(f: List[Category] => List[Category]): Unit =
    synchronized { fakeCategories = f(fakeCategories) }

  override def save(category: Category): Future[Long] = {
    safeModify(_ :+ category)
    Future(category.id)
  }

  override def all(): Future[Seq[Category]] = Future(fakeCategories)

  override def findById(id: Long): Future[Option[Category]] =
    Future(fakeCategories.find(_.id == id))

  override def deleteById(id: Long): Future[Boolean] = {
    val isPresent = fakeCategories.exists(_.id == id)
    safeModify(_.filterNot(_.id == id))
    Future(isPresent)
  }
}
