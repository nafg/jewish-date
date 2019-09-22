ThisBuild / organization := "io.github.nafg"

ThisBuild / crossScalaVersions := Seq("2.12.8", "2.13.0")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.last

ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

lazy val jewishDate =
  crossProject.crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "jewish-date",
      version := "0.1.1-SNAPSHOT",
      libraryDependencies += "io.monix" %%% "minitest" % "2.7.0" % "test",
      testFrameworks += new TestFramework("minitest.runner.Framework")
    )
    .jvmSettings(
      libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % "test",
      libraryDependencies +=
        ("KosherJava" % "zmanim" % "1.4.0alpha" % "test")
          .from("https://github.com/KosherJava/zmanim/raw/master/lib/zmanim-1.4.0alpha.jar"),
      Test / testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-minSuccessfulTests", "10000")
    )
    .jsSettings(
      libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC3"
    )
lazy val jewishDateJS = jewishDate.js
lazy val jewishDateJVM = jewishDate.jvm
