package org.example.registrations

import com.typesafe.config._
import org.testcontainers.containers.PostgreSQLContainer
import org.slf4j.LoggerFactory
import java.security.MessageDigest

class PostgreSQL(initScript: String) {

  private val logger = LoggerFactory.getLogger(this.getClass)

  private val container: PostgreSQLContainer[_] = {
    val psql: PostgreSQLContainer[_] = new PostgreSQLContainer("postgres").withInitScript(initScript)
    // psql.withEnv("password_encryption", "scram-sha-256")
    logger.info(s"Starting container...")
    psql.start()
    psql
  }

  def stop() = {
    logger.info("Stopping container...")
    container.stop()
  }

  val config: Config = {
    val components = List(
      container.getJdbcUrl,
      s"user=${container.getUsername}",
      // s"password=${MessageDigest.getInstance("MD5").digest(container.getPassword.getBytes())}"
      s"password=${container.getPassword}"
    )
    ConfigFactory
      .empty()
      .withValue(
        "url",
        ConfigValueFactory.fromAnyRef(components.mkString("&"))
      )
  }
}

// import org.example.registrations._
// import scala.concurrent.ExecutionContext.Implicits.global
//
// val queries = new TestQueries(TestDatabase.ctx)
// queries.testConnection()
