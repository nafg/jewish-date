import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

ThisBuild / organization := "io.github.nafg.jewish-date"

ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.6")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.last

ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

publish / skip := true

lazy val jewishDate =
  crossProject(JVMPlatform, JSPlatform).crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "jewish-date",
      libraryDependencies += "io.monix" %%% "minitest" % "2.9.6" % Test,
      testFrameworks += new TestFramework("minitest.runner.Framework")
    )
    .jvmSettings(
      libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.4" % Test,
      libraryDependencies += ("com.kosherjava" % "zmanim" % "2.02" % Test),
      Test / testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-minSuccessfulTests", "10000")
    )
    .jsSettings(
      libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.3.0"
    )
lazy val jewishDateJS = jewishDate.js
lazy val jewishDateJVM = jewishDate.jvm
