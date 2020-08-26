ThisBuild / publishTo :=
  Some("Project Bintray" at "https://api.bintray.com/maven/naftoligug/maven/jewish-date;publish=1")

sys.env.get("BINTRAYKEY").toSeq.map { key =>
  credentials in ThisBuild += Credentials(
    "Bintray API Realm",
    "api.bintray.com",
    "naftoligug",
    key
  )
}
