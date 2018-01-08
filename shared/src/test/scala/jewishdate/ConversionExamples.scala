package jewishdate

import java.time.LocalDate

import minitest.SimpleTestSuite


object ConversionExamples extends SimpleTestSuite {
  test("Example 1") {
    assertEquals(JewishDate(LocalDate.of(842, 3, 31)), JewishDate(4602, JewishMonth.Nissan, 12))
  }
  test("Example 2") {
    assertEquals(JewishDate(LocalDate.of(1582, 10, 11)), JewishDate(5343, JewishMonth.Tishrei, 15))
  }
  test("Example 3") {
    assertEquals(JewishDate(LocalDate.of(1582, 10, 14)), JewishDate(5343, JewishMonth.Tishrei, 18))
  }
}
