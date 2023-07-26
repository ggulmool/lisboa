package me.ggulmool.lisboa.domain.profits

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.ggulmool.lisboa.domain.common.Money

class YearProfitsTest : StringSpec({

    "연간 영업이익 X" {
        val yearProfits = YearProfits()
        yearProfits.hasYearProfits() shouldBe false
    }

    "연간 영업이익 O" {
        val yearProfits = YearProfits(
            mutableMapOf("2023" to Money("30000"))
        )
        yearProfits.hasYearProfits() shouldBe true
    }
})
