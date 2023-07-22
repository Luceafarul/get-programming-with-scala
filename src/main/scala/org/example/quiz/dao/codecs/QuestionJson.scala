package org.example.quiz.dao.codecs

import io.circe._
import io.circe.generic.semiauto._
import org.example.quiz.dao.records.Question

object QuestionJson {
  implicit val encoder: Encoder[Question] = deriveEncoder[Question]
  implicit val decoder: Decoder[Question] = deriveDecoder[Question]
}
