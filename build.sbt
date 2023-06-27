name := "get-programming-with-scala-lesson46"

version := "0.1"

scalaVersion := "2.13.11" // quill doesn't support scala 3 yet

libraryDependencies ++= List(
  // "io.getquill"       %% "quill-jasync-postgres" % "3.7.2",
  "io.getquill"       %% "quill-jasync-postgres" % "4.6.0",
  "org.testcontainers" % "postgresql"            % "1.18.3",
  "org.postgresql"     % "postgresql"            % "42.5.4",
  "ch.qos.logback"     % "logback-classic"       % "1.2.3"
)
