package org.example.books.entities

final case class User(id: Long, fullName: String)

final case class BookLoan(book: Book, user: User)
