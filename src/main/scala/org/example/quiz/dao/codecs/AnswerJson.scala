package org.example.quiz.dao.codecs

import io.circe._
import io.circe.generic.semiauto._
import org.example.quiz.dao.records.Answer

object AnswerJson {
  implicit val encoder: Encoder[Answer] = deriveEncoder[Answer]
  implicit val decoder: Decoder[Answer] = deriveDecoder[Answer]
}
