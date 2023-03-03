package org.example.movies

import PrintResultHelpers._
import org.example.movies.entities.Movie
import java.text.NumberFormat

object Main extends App {
  val dataset = new MoviesDataset("movies_metadata.csv")
  val movies  = dataset.movies

  private val unknown = "--"

  printResult(
    question = "How many movies are there in the dataset?",
    answer = s"${movies.size} movies"
  )

  printResult(
    question = "How many movies were released in 1987?",
    answer = s"${movies.count(_.releaseDate.exists(_.getYear == 1987))} movies"
  )

  printResult(
    question = "TOP 5 movies per vote average and count?",
    answers = {
      val topPerVote = movies.filter(_.voteCount >= 50).sortBy(movie => (-movie.voteAverage, -movie.voteCount)).take(5)
      topPerVote.map(movie => s"[AVG: ${movie.voteAverage}, COUNT: ${movie.voteCount}] ${movie.title}")
    }
  )

  printResult(
    question = "TOP 5 movies per popularity?",
    answers = {
      val topPerPopularity = movies.sortBy(-_.popularity.getOrElse[Float](0)).take(5)
      topPerPopularity.map(movie => s"[POPULARITY: ${movie.popularity.getOrElse(unknown)}] ${movie.title}")
    }
  )

  printResult(
    question = "5 non-enlish movies?",
    answers = {
      val nonEnglishMovies = movies.filterNot(_.originalLanguage.toLowerCase == "en")
      nonEnglishMovies.take(5).map(movie => s"[LANG: ${movie.originalLanguage}] ${movie.title}")
    }
  )

  printResult(
    question = "Which movie made the most profit?",
    answer = {
      val mostProfitMovie = movies.maxBy(movie => movie.revenue - movie.budget)
      val formattedProfit = NumberFormat.getInstance().format(mostProfitMovie.revenue - mostProfitMovie.budget)
      s"[PROFIT: USD $formattedProfit] ${mostProfitMovie.title}"
    }
  )
}
