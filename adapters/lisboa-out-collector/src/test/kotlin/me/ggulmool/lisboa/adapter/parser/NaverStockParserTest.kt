package me.ggulmool.lisboa.adapter.parser

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.ggulmool.lisboa.domain.stock.MarketType
import me.ggulmool.lisboa.domain.stock.Sector

class NaverStockParserTest : StringSpec({

    "종목 파싱 테스트" {
        val cut = NaverStockParser()

        val actual = cut.parse("035420")
        actual.name shouldBe "NAVER"
        actual.sector shouldBe Sector.INTERACTIVE_MEDIA_AND_SERVICES
        actual.marketType shouldBe MarketType.KOSPI

        val actual2 = cut.parse("023590")
        actual2.name shouldBe "다우기술"
        actual2.sector shouldBe Sector.STOCK
        actual2.marketType shouldBe MarketType.KOSPI

        val actual3 = cut.parse("003670")
        actual3.name shouldBe "포스코퓨처엠"
    }
})
