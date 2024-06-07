import _root_.io.github.nafg.scalacoptions.{ScalacOptions, options}

import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}


ThisBuild / organization := "io.github.nafg.jewish-date"

ThisBuild / scalaVersion := "3.3.3"
ThisBuild / crossScalaVersions := List("2.13.14", (ThisBuild / scalaVersion).value)

ThisBuild / scalacOptions ++=
  ScalacOptions.all(scalaVersion.value)(
    (o: options.Common) => o.deprecation ++ o.unchecked ++ o.feature
  )

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
      libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.18.0" % Test,
      libraryDependencies += ("com.kosherjava" % "zmanim" % "2.5.0" % Test),
      Test / testOptions += Tests.Argument(TestFrameworks.ScalaCheck, "-minSuccessfulTests", "10000")
    )
    .jsSettings(
      libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.6.0"
    )
lazy val jewishDateJS = jewishDate.js
lazy val jewishDateJVM = jewishDate.jvm
