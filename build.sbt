ThisBuild / organization := "io.github.nafg"
ThisBuild / scalaVersion := "2.12.4"
ThisBuild / scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

lazy val jewishDate =
  crossProject.crossType(CrossType.Full)
    .in(file("."))
    .settings(
      name := "jewish-date",
      version := "0.1.1-SNAPSHOT",
      libraryDependencies += "io.monix" %%% "minitest" % "2.4.0" % "test",
      testFrameworks += new TestFramework("minitest.runner.Framework")
    )
    .jvmSettings(
      libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
      libraryDependencies +=
        ("KosherJava" % "zmanim" % "1.4.0alpha" % "test")
          .from("https://github.com/KosherJava/zmanim/raw/master/lib/zmanim-1.4.0alpha.jar"),
      Test / testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-minSuccessfulTests", "10000")
    )
    .jsSettings(
      libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC2"
    )
lazy val jewishDateJS = jewishDate.js
lazy val jewishDateJVM = jewishDate.jvm
