package jewishdate

import java.time.LocalDate


case class JewishDate(year: JewishYear, month: JewishMonth.Value, dayOfMonth: Int) {
  def dayOfYear: Int = {
    val priorMonths = year.monthsIterator(JewishMonth.Tishrei).takeWhile(_ != month)
    priorMonths.map(year.monthLength).sum + dayOfMonth
  }

  def isYomTov(yomTov: YomTov) = {
    val d = day - yomTov.startDate.day
    d >= 0 && d < yomTov.length
  }

  def yomTov = year.yomimTovim.find(isYomTov)

  /**
    * This day, counting from the first day of the Jewish calendar
    */
  lazy val day = year.firstDay + dayOfYear

  def next = JewishDate.fromDay(day + 1)
  def prev = JewishDate.fromDay(day - 1)

  def toLocalDate: LocalDate = LocalDate.ofEpochDay(day + JewishDate.JewishEpoch)
}

object JewishDate {
  /**
    * The start of the Jewish calendar as Epoch Day
    */
  val JewishEpoch = -2092592L

  def apply(year: Int, month: JewishMonth.Value, dayOfMonth: Int, dummy: Null = null): JewishDate =
    new JewishDate(new JewishYear(year), month, dayOfMonth)

  def fromDay(jewishDay: Long): JewishDate = {
    val jewishYearGuess = new JewishYear((jewishDay / 366).toInt)
    val jewishYear =
      Iterator.iterate(jewishYearGuess)(_.next)
        .dropWhile(_.next.firstDay < jewishDay)
        .next()

    val monthSearchStart =
      if (jewishDay < new JewishDate(jewishYear, JewishMonth.Nissan, 1).day)
        JewishMonth.Tishrei
      else
        JewishMonth.Nissan
    val jewishMonth =
      jewishYear.monthsIterator(monthSearchStart)
        .dropWhile(m => jewishDay > new JewishDate(jewishYear, m, jewishYear.monthLength(m)).day)
        .next()
    val firstDayOfMonth = new JewishDate(jewishYear, jewishMonth, 1)
    val actualDayOfMonth = jewishDay - firstDayOfMonth.day + 1
    firstDayOfMonth.copy(dayOfMonth = actualDayOfMonth.toInt)
  }

  def apply(localDate: LocalDate): JewishDate = fromDay(localDate.toEpochDay - JewishEpoch)

  implicit val ordering: Ordering[JewishDate] = Ordering.by(_.day)
}
