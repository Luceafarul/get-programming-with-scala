package org.example.quiz.api

import org.example.quiz.service.GenericService
import org.http4s.dsl.Http4sDsl
import cats.effect.IO
import org.http4s.HttpRoutes

class GenericApi(genericService: GenericService) extends Http4sDsl[IO] {
  val routes = HttpRoutes.of[IO] {
    case GET -> Root / "ping"        => Ok("pong")
    case GET -> Root / "healthcheck" => Ok(genericService.healthCheck)
  }
}
