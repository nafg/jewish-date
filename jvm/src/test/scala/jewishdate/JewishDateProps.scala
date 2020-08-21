package jewishdate

import java.time._

import com.kosherjava.zmanim.hebrewcalendar.{JewishCalendar => KJCal}
import org.scalacheck.Prop._
import org.scalacheck.{Gen, Prop, Properties}


class JewishDateProps extends Properties("jewishDate") {
  val genJewishDate: Gen[JewishDate] =
    for {
      year <- Gen.choose(3762, 6000).map(new JewishYear(_))
      month <- Gen.oneOf(year.monthsIterator.toSeq)
      day <- Gen.choose(1, year.monthLength(month))
    } yield JewishDate(year, month, day)


  property("toLocalDate") =
    Prop.forAll(genJewishDate) { date =>
      val jc = new KJCal(date.year.value, date.month.id, date.dayOfMonth)
      date.toLocalDate ?=
        LocalDate.of(jc.getGregorianYear, jc.getGregorianMonth + 1, jc.getGregorianDayOfMonth)
    }
}
