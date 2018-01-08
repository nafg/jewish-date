package jewishdate

import java.time.DayOfWeek


class JewishYear(val value: Int) extends AnyVal {
  override def toString = value.toString

  def next = new JewishYear(value + 1)

  def prev = new JewishYear(value - 1)

  def isLeap = (7 * value + 1) % 19 < 7

  def numMonths = if (isLeap) 13 else 12

  /**
    * The first day of this year, counting from the first day of the Jewish calendar
    */
  def firstDay = {
    val monthsPriorMetonicCycles = 235 * ((value - 1) / 19)
    val nonLeapMonthsThisCycle = 12 * ((value - 1) % 19)
    val leapMonthsThisCycle = (7 * ((value - 1) % 19) + 1) / 19
    val monthsPassed = monthsPriorMetonicCycles + nonLeapMonthsThisCycle + leapMonthsThisCycle
    val chalakimFromMoladTohu = JewishYear.ChalakimMoladTohu + JewishYear.ChalakimPerMonth * monthsPassed.toLong
    val moladDay = (chalakimFromMoladTohu / JewishYear.ChalakimPerDay).toInt
    val moladChalakimInDay = (chalakimFromMoladTohu - moladDay.toLong * JewishYear.ChalakimPerDay).toInt

    // GaTRaD - If on a non leap year, the molad of Tishrei falls on a Tuesday (Ga) on or after 9 hours (T) and 204
    // chalakim (RaD) it is delayed till Thursday (one day delay, plus one day for Lo ADU Rosh)
    val dechiyaGaTRaD = !isLeap && moladDay % 7 == 2 && moladChalakimInDay >= 9924

    // BeTuTaKFoT - if the year following a leap year falls on a Monday (Be) on or after 15 hours (Tu) and 589
    // chalakim (TaKFoT) it is delayed till Tuesday
    val dechiyaBeTuTaKFot = prev.isLeap && moladDay % 7 == 1 && moladChalakimInDay >= 16789

    // Molad Zaken - If the molad of Tishrei falls after 12 noon, Rosh Hashana is delayed to the following day. If
    // the following day is ADU, it will be delayed an additional day.
    val dechiyaMoladZaken = moladChalakimInDay >= 19440

    val day0 = if (dechiyaMoladZaken || dechiyaGaTRaD || dechiyaBeTuTaKFot) moladDay + 1 else moladDay

    // Lo ADU Rosh - Rosh Hashana can't fall on a Sunday, Wednesday or Friday. If the molad fell on one of these
    // days, Rosh Hashana is delayed to the following day.
    val dechiyaLoADURosh = day0 % 7 == 0 || day0 % 7 == 3 || day0 % 7 == 5

    val withDechiyos = if (dechiyaLoADURosh) day0 + 1 else day0

    withDechiyos
  }

  /**
    * The number of days in this Jewish year
    */
  def length = next.firstDay - firstDay

  def isCheshvanLong = length % 10 == 5

  def isKislevLong = length % 10 != 3

  /**
    * Returns an Iterator of the months of this year, starting from Nissan
    */
  def monthsIterator: Iterator[JewishMonth.Value] = JewishMonth.values.iterator.take(numMonths)

  /**
    * Retuns an Iterator of the months of this year, starting from the given month.
    */
  def monthsIterator(first: JewishMonth.Value): Iterator[JewishMonth.Value] = {
    val (before, fromStart) = monthsIterator.span(_ != first)
    fromStart ++ before
  }

  def monthLength(month: JewishMonth.Value) = month match {
    case JewishMonth.Nissan | JewishMonth.Sivan | JewishMonth.Av | JewishMonth.Tishrei | JewishMonth.Shvat => 30
    case JewishMonth.Cheshvan if isCheshvanLong                                                            => 30
    case JewishMonth.Kislev if isKislevLong                                                                => 30
    case JewishMonth.Adar if isLeap                                                                        => 30
    case _                                                                                                 => 29
  }

  private def date(month: JewishMonth.Value, dayOfMonth: Int) = JewishDate(this, month, dayOfMonth)

  def pesachFirst = YomTov("Pesach (first days)", date(JewishMonth.Nissan, 15), 2, melachaForbidden = true)
  def pesachCholHamoed = YomTov("Chol Hamoed Pesach", date(JewishMonth.Nissan, 17), 4, melachaForbidden = false)
  def pesachLast = YomTov("Pesach (last days)", date(JewishMonth.Nissan, 21), 2, melachaForbidden = true)
  def shavuos = YomTov("Shavuos", date(JewishMonth.Sivan, 6), 2, melachaForbidden = true)
  def tishaBAv = {
    val d = date(JewishMonth.Av, 9)
    val d2 = if (d.toLocalDate.getDayOfWeek == DayOfWeek.SATURDAY) d.next else d
    YomTov("Tisha B'Av", d2, 1, melachaForbidden = false)
  }
  def roshHashanah = YomTov("Rosh Hashanah", date(JewishMonth.Tishrei, 1), 2, melachaForbidden = true)
  def yomKippur = YomTov("Yom Kippur", date(JewishMonth.Tishrei, 10), 1, melachaForbidden = true)
  def sukkosFirst = YomTov("Sukkos (first days)", date(JewishMonth.Tishrei, 15), 2, melachaForbidden = true)
  def sukkosCholHamoed =
    YomTov("Chol Hamoed Sukkos", date(JewishMonth.Tishrei, 17), 4, melachaForbidden = false)
  def hoshanahRabbah = YomTov("Hoshanah Rabbah", date(JewishMonth.Tishrei, 21), 1, melachaForbidden = false)
  def sheminiAtzeres = YomTov("Shemini Atzeres", date(JewishMonth.Tishrei, 22), 1, melachaForbidden = true)
  def simchasTorah = YomTov("Simchas Torah", date(JewishMonth.Tishrei, 23), 1, melachaForbidden = true)
  def chanukah = YomTov("Chanukah", date(JewishMonth.Kislev, 25), 8, melachaForbidden = false)
  def purim =
    YomTov("Purim", date(if (isLeap) JewishMonth.`Adar Sheni` else JewishMonth.Adar, 14), 1, melachaForbidden = false)
  def shushanPurim =
    YomTov("Shushan Purim", date(if (isLeap) JewishMonth.`Adar Sheni` else JewishMonth.Adar, 15), 1, melachaForbidden = false)

  def yomimTovim = {
    Seq(
      pesachFirst,
      pesachCholHamoed,
      pesachLast,
      shavuos,
      roshHashanah,
      yomKippur,
      sukkosFirst,
      sukkosCholHamoed,
      hoshanahRabbah,
      sheminiAtzeres,
      simchasTorah,
      chanukah,
      purim,
      shushanPurim
    )
  }
}

object JewishYear {
  val ChalakimMoladTohu = 31524L
  val ChalakimPerMonth = 765433L
  val ChalakimPerDay = 25920L
}

