package org.example.quiz.services

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.concurrent.ExecutionContext
import cats.effect.ContextShift
import cats.effect.IO
import org.example.quiz.service.CategoryService
import org.example.quiz.stubs.dao.FakeCategoryDao
import org.example.quiz.stubs.dao.Fixtures

class CategoryServiceTest extends AnyFlatSpec with Matchers {
  private def makeService() = {
    implicit val ec: ExecutionContext = ExecutionContext.global
    implicit val cs: ContextShift[IO] = IO.contextShift(ec)

    new CategoryService(new FakeCategoryDao)
  }

  private val service = makeService()

  "CategoryService" should
    "return all the categories" in {
      val actual   = service.all().unsafeRunSync()
      val expected = Fixtures.categories

      actual shouldBe expected
    }

  it should "return a category if the id exist" in {
    val actual   = service.get(Fixtures.categoryA.id).unsafeRunSync()
    val expected = Fixtures.categoryA

    actual shouldBe Option(expected)
  }

  it should "return no category if the id is invalid" in {
    val actual = service.get(-1L).unsafeRunSync()

    actual shouldBe None
  }
}
