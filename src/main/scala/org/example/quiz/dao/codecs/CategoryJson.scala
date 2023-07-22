package org.example.quiz.dao.codecs

import io.circe._
import io.circe.generic.semiauto._
import org.example.quiz.dao.records.Category

object CategoryJson {
  implicit val encoder: Encoder[Category] = deriveEncoder[Category]
  implicit val decoder: Decoder[Category] = deriveDecoder[Category]
}
