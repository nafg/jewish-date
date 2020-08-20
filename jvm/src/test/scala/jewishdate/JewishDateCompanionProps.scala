package jewishdate

import java.time.{LocalDate, Month, Year}
import java.util.GregorianCalendar

import net.sourceforge.zmanim.hebrewcalendar.{JewishCalendar => KJCal}
import org.scalacheck.Prop._
import org.scalacheck.{Gen, Prop, Properties}


class JewishDateCompanionProps extends Properties("JewishDate") {
  val genLocalDate: Gen[LocalDate] =
    for {
      year <- Gen.choose(1583, 3000)
      month <- Gen.choose(1, 12)
      day <- Gen.choose(1, Month.of(month).length(Year.isLeap(year)))
    } yield LocalDate.of(year, month, day)

  property("apply") =
    Prop.forAll(genLocalDate) { date =>
      val jc = new KJCal(new GregorianCalendar(date.getYear, date.getMonthValue - 1, date.getDayOfMonth))
      val jd = JewishDate(date)
      jd ?=
        JewishDate(new JewishYear(jc.getJewishYear), JewishMonth(jc.getJewishMonth), jc.getJewishDayOfMonth)
    }
}
