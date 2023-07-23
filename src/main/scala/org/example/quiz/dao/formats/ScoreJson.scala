package org.example.quiz.dao.formats

import io.circe._
import io.circe.generic.semiauto._
import org.example.quiz.dao.domain.Score

object ScoreJson {
  import GivenAnswerJson._

  implicit val scoreEncoder: Encoder[Score] = deriveEncoder[Score]
  implicit val scoreDecoder: Decoder[Score] = deriveDecoder[Score]
}
