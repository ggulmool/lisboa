package me.ggulmool.lisboa.domain.common

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class QuarterTest : FunSpec({
    context("input month output Quarter") {
        withData(
            nameFn = { "${it.month} to ${it.expectedQuarter}" },
            QuarterTestData("01", Quarter.FIRST),
            QuarterTestData("02", Quarter.FIRST),
            QuarterTestData("03", Quarter.FIRST),
            QuarterTestData("04", Quarter.SECOND),
            QuarterTestData("05", Quarter.SECOND),
            QuarterTestData("06", Quarter.SECOND),
            QuarterTestData("07", Quarter.THIRD),
            QuarterTestData("08", Quarter.THIRD),
            QuarterTestData("09", Quarter.THIRD),
            QuarterTestData("10", Quarter.FOURTH),
            QuarterTestData("11", Quarter.FOURTH),
            QuarterTestData("12", Quarter.FOURTH),
        ) {
            (month, expectedQuarter) -> Quarter[month] shouldBe expectedQuarter
        }
    }

    context("01~12이외의 값이 들어오면 IllegalArgumentException 발생") {
        shouldThrow<IllegalArgumentException> { Quarter["13"] }
        shouldThrow<IllegalArgumentException> { Quarter["45"] }
    }
})

data class QuarterTestData(val month: String, val expectedQuarter: Quarter)
