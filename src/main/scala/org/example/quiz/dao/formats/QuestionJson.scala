package org.example.quiz.dao.formats

import io.circe._
import io.circe.generic.semiauto._
import org.example.quiz.dao.domain.Question

object QuestionJson {
  implicit val encoder: Encoder[Question] = deriveEncoder[Question]
  implicit val decoder: Decoder[Question] = deriveDecoder[Question]
}
