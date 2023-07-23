package org.example.quiz.dao.formats

import io.circe._
import io.circe.generic.semiauto._
import org.example.quiz.dao.domain.Answer

object AnswerJson {
  implicit val encoder: Encoder[Answer] = deriveEncoder[Answer]
  implicit val decoder: Decoder[Answer] = deriveDecoder[Answer]
}
