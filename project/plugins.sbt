addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.1.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.8.0")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.10")
addSbtPlugin("com.codecommit" % "sbt-github-actions" % "0.13.0")
libraryDependencies += "io.github.nafg.mergify" %% "mergify-writer" % "0.3.0"
