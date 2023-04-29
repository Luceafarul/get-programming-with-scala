package org.example.books.entities

import scala.util.{ Failure, Success, Try }
import java.net.URL

final case class Book(
  id: Long,
  title: String,
  authors: List[String],
  imageUrl: Option[URL]
):
  def toPrettyString: String =
    s"[$id] $title ${authors.mkString("{", ", ", "}")}"

object Book:
  def parse(row: Map[String, String]): Try[Book] =
    for {
      id       <- parseAs[Long](row, "goodreads_book_id", _.toLong)
      title    <- parseAs[String](row, "title", identity)
      authors  <- parseAs[List[String]](row, "authors", _.split(",").toList)
      imageUrl <- parseAs[Option[URL]](row, "image_url", s => Option(new URL(s)))
    } yield Book(id, title, authors, imageUrl)
  // for {
  //   stringId      <- row.get("goodreads_book_id")
  //   title         <- row.get("original_title")
  //   stringAuthors <- row.get("authors")
  // } yield {
  //   for {
  //     id        <- Try(stringId.toLong)
  //     authors   <- Try(stringAuthors.split(",").toList)
  //     stringUrl <- Try(row.get("image_url"))
  //   } yield Book(id, title, authors, stringUrl.map(URL(_)))
  // }

  private def parseAs[A](row: Map[String, String], key: String, parser: String => A): Try[A] =
    for {
      value <- getValue(row, key)
      a     <- Try(parser(value))
    } yield a

  private def getValue(row: Map[String, String], key: String): Try[String] =
    row.get(key) match
      case Some(value) => Success(value)
      case None        => Failure(new IllegalArgumentException(s"Couldn't find column: $key in row: $row"))
