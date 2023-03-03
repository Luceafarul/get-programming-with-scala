package org.example.movies.entities

import java.time.LocalDate
import org.slf4j.LoggerFactory

case class Genre(id: Int, name: String)

case class Movie(
  genres: List[Genre],
  id: Int,
  imdbId: String,
  originalLanguage: String,
  originalTitle: String,
  title: String,
  overview: String,
  popularity: Option[Float],
  releaseDate: Option[LocalDate],
  revenue: Int,
  budget: Int,
  duration: Option[Double],
  voteAverage: Float,
  voteCount: Float
)

object Movie {
  import Parsers._

  private val logger = LoggerFactory.getLogger(this.getClass)

  def parse(row: Map[String, String]): Option[Movie] = {
    val movie =
      for {
        genres           <- parseGenres(row, "genres")
        id               <- parseInt(row, "id")
        imdbId           <- parseString(row, "imdb_id")
        originalLanguage <- parseString(row, "original_language")
        originalTitle    <- parseString(row, "original_title")
        budget           <- parseInt(row, "budget")
        overview         <- parseString(row, "overview")
        title             = parseString(row, "title").getOrElse(originalTitle)
        popularity        = parseFloat(row, "popularity")
        releaseDate       = parseLocalDate(row, "release_date")
        revenue           = parseInt(row, "revenue").getOrElse(0)
        duration          = parseDouble(row, "duration")
        voteAverage       = parseFloat(row, "vote_average").getOrElse[Float](0)
        voteCount         = parseFloat(row, "vote_count").getOrElse[Float](0)
      } yield Movie(
        genres,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        title,
        overview,
        popularity,
        releaseDate,
        revenue,
        budget,
        duration,
        voteAverage,
        voteCount,
      )
    if movie.isEmpty then logger.warn(s"Skipping malformed moview row")
    movie
  }
}
