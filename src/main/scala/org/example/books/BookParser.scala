package org.example.books

import org.example.books.entities.Book
import com.github.tototoshi.csv._
import org.slf4j.{ Logger, LoggerFactory }

import scala.io.Source
import scala.util.{ Failure, Success, Try }

class BookParser(filePath: String) {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  val books: List[Book] = loadCSVFile(filePath).foldLeft(List.empty) { (books, row) =>
    Book.parse(row) match
      case Success(book) => books :+ book
      case Failure(exception) =>
        logger.warn(s"Parsing failed: ${exception.getMessage}")
        books
  }

  private def loadCSVFile(path: String): List[Map[String, String]] = {
    logger.info(s"Processing file $path...")
    val file   = Source.fromResource(path, classOf[BookParser].getClassLoader)
    val reader = CSVReader.open(file)
    val data   = reader.allWithHeaders()
    logger.info(s"Completed processing of file $path! ${data.size} records loaded")
    data
  }
}
