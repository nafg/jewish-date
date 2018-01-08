publishMavenStyle in ThisBuild := true

publishTo in ThisBuild := Some("Project Bintray" at "https://api.bintray.com/maven/naftoligug/maven/jewish-date")

sys.env.get("BINTRAYKEY").toSeq.map { key =>
  credentials in ThisBuild += Credentials(
    "Bintray API Realm",
    "api.bintray.com",
    "naftoligug",
    key
  )
}
