updateOptions := updateOptions.value.withLatestSnapshots(false)

lazy val root = (project in file("."))
  .settings(
    name         := "CustomMLBase",
    organization := "edu.trinity",
    scalaVersion := "2.12.8",
    version      := "0.1.0-SNAPSHOT",
		libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8",
		libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

  )
