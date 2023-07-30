package me.ggulmool.lisboa.domain.stock

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.common.Quarter
import me.ggulmool.lisboa.domain.profits.QuarterProfit
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import java.math.BigDecimal

class StockTest : StringSpec({

    val cut = Stock(
        "035720",
        MarketType.KOSPI,
        "카카오", Money("353,500"), StockQuantity("88,247,819"),
        Sector.INTERACTIVE_MEDIA_AND_SERVICES,
        null,
        YearProfits(
            mutableMapOf(
                "2017" to Money("165,400,000,000"),
                "2018" to Money("72,900,000,000"),
                "2019" to Money("206,800,000,000"),
                "2020" to Money("439,400,000,000")
            )
        ),
        QuarterProfits(
            mutableMapOf(
                "2019" to QuarterProfit(
                    mutableMapOf(
                        Quarter.FIRST to Money("40,500,000,000"),
                        Quarter.SECOND to Money("40,500,000,000"),
                        Quarter.THIRD to Money("59,100,000,000"),
                        Quarter.FOURTH to Money("79,600,000,000")
                    )
                ),
                "2020" to QuarterProfit(
                    mutableMapOf(
                        Quarter.FIRST to Money("88,200,000,000"),
                        Quarter.SECOND to Money("97,800,000,000"),
                        Quarter.THIRD to Money("115,500,000,000")
                    )
                )
            )
        ),
        ""
    )

    "시가총액 계산" {
        cut.calculateMarketCapitalization(Money("1,500"), StockQuantity("10")) shouldBe Money("15000")
        cut.calculateMarketCapitalization() shouldBe Money("31,195,604,016,500") // 31조 1,956억
    }

    "목표시가총액 계산" {
        cut.calculateTargetMarketCapitalization("2019") shouldBe Money("3,102,000,000,000")
        cut.calculateTargetMarketCapitalization("2020") shouldBe Money("6,591,000,000,000")
    }

    "상승여력 계산" {
        cut.increaseSpareCapacity(Money("3,232"), Money("1,500")) shouldBe BigDecimal("116.0")
        cut.increaseSpareCapacity(Money("3,000"), Money("1,500")) shouldBe BigDecimal("100.0")
    }

    "목표주가 계산" {
        cut.targetPrice("2020") shouldBe Money("74,688")
    }
})
