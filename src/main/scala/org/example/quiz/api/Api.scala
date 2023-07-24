package org.example.quiz.api

import org.example.quiz.service.Services

class Api(services: Services) {
  val generic  = new GenericApi(services.generic)
  val category = new CategoryApi(services.category)
  val quiz     = new QuizApi(services.quiz) 
}
