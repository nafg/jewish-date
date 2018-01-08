package jewishdate

import minitest.SimpleTestSuite


object YomTovExamples extends SimpleTestSuite {
  test("Yomim Tovim") {
    val year = new JewishYear(5778)
    assert(JewishDate(year, JewishMonth.Nissan, 22).isYomTov(year.pesachLast))
    assert(JewishDate(year, JewishMonth.Nissan, 23).yomTov.isEmpty)
  }
}
