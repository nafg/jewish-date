# jewish-date

#### A Scala library for converting between the Gregorian calendar and the Jewish calendar, cross-compiled for JVM and JS

Heavily based on and derived from https://github.com/KosherJava/zmanim, but taking advantage of the Java 8 `java.time` API, and as a Scala-idiomatic API.

### Features

* Convert from `java.time.LocalDate` to `jewishdate.JewishDate` and vice versa
* Get date of Yomim Tovim and Yom Tov of a date (currently Diaspora only; pull requests welcome)
