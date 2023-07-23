package org.example.quiz.dao.formats

import io.circe._
import io.circe.generic.semiauto._
import org.example.quiz.dao.domain.GivenAnswer

object GivenAnswerJson {
  implicit val givenAnswerEncoder: Encoder[GivenAnswer] = deriveEncoder[GivenAnswer]
  implicit val givenAnswerDecoder: Decoder[GivenAnswer] = deriveDecoder[GivenAnswer]
}
