name := "get-programming-with-scala-lesson46"

version := "0.1"

scalaVersion := "3.3.0"

libraryDependencies ++= List(
  "io.getquill"       %% "quill-jasync-postgres" % "4.6.0",
  "org.testcontainers" % "postgresql"            % "1.18.3",
  "org.postgresql"     % "postgresql"            % "42.5.4",
  "ch.qos.logback"     % "logback-classic"       % "1.2.3"
)
