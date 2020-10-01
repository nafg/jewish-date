import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / organization := "io.github.nafg"

ThisBuild / crossScalaVersions := Seq("2.12.10", "2.13.1")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.last

ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

publish / skip := true

lazy val jewishDate =
  crossProject(JVMPlatform, JSPlatform).crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "jewish-date",
      version := "0.3.0",
      libraryDependencies += "io.monix" %%% "minitest" % "2.8.2" % Test,
      testFrameworks += new TestFramework("minitest.runner.Framework")
    )
    .jvmSettings(
      libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.3" % Test,
      libraryDependencies += ("com.kosherjava" % "zmanim" % "2.02" % Test),
      Test / testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-minSuccessfulTests", "10000")
    )
    .jsSettings(
      libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0"
    )
lazy val jewishDateJS = jewishDate.js
lazy val jewishDateJVM = jewishDate.jvm
