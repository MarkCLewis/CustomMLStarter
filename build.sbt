updateOptions := updateOptions.value.withLatestSnapshots(false)

lazy val root = (project in file("."))
  .settings(
    name         := "CustomMLBase",
    organization := "edu.trinity",
    scalaVersion := "2.12.12",
    version      := "0.1.0-SNAPSHOT",
		libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2",
		libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"

  )
