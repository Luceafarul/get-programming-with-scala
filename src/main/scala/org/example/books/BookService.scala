package org.example.books

import org.example.books.entities._
import org.slf4j.{ Logger, LoggerFactory }

class BookService(bookCatalogPath: String) {

  private val logger: Logger = LoggerFactory.getLogger(this.getClass)

  private val books: List[Book] = new BookParser(bookCatalogPath).books

  private var bookLoans: Set[BookLoan] = Set.empty

  def search(title: Option[String] = None, author: Option[String] = None): List[Book] =
    books.filter { book =>
      title.forall(t => searchIgnoreCase(book.title, t)) &&
      author.forall(a => book.authors.exists(searchIgnoreCase(_, a)))
    }

  def reserveBook(bookId: Long, user: User): Either[String, BookLoan] =
    for {
      _    <- checkResrvationLimit(user)
      book <- checkIsBookExist(bookId)
      _    <- isBookAvailableToReserve(book)
    } yield
      val loanBook = BookLoan(book, user)
      synchronized { bookLoans = bookLoans + loanBook }
      logger.info(s"Book with id: $bookId loan by user: ${user.id}")
      loanBook

  def returnBook(bookId: Long): Either[String, BookLoan] =
    for {
      book <- checkIsBookExist(bookId)
      user <- checkIsBookTaken(book)
    } yield
      val loanBook = BookLoan(book, user)
      synchronized { bookLoans = bookLoans - loanBook }
      logger.info(s"Book with id: $bookId returned by user: ${user.id}")
      loanBook

  private def searchIgnoreCase(text: String, substring: String): Boolean =
    text.toLowerCase.contains(substring.toLowerCase)

  private def checkResrvationLimit(user: User, limit: Int = 5): Either[String, User] =
    if bookLoans.count(_.user.id == user.id) < limit then Right(user) else Left("You can't loan more then 5 books")

  private def checkIsBookExist(bookId: Long): Either[String, Book] =
    books.find(_.id == bookId) match
      case Some(book) => Right(book)
      case _          => Left(s"Book with id: $bookId does not exist")

  private def isBookAvailableToReserve(book: Book): Either[String, Book] =
    if bookLoans.exists(_.book == book) then Left(s"Book: ${book.title} with id: ${book.id} already loaned")
    else Right(book)

  private def checkIsBookTaken(book: Book): Either[String, User] =
    bookLoans.find(_.book.id == book.id) match
      case Some(loanBook) => Right(loanBook.user)
      case _              => Left(s"Book with id: ${book.id} does not taken")
}
