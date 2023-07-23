package me.ggulmool.lisboa.adapter.collect

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize

class StockListCollectorTest : StringSpec({

    "종목 리스트 가져오기" {
        val cut: StockListCollector = StockListCollector()
        val actual = cut.getStockList()
        actual.shouldHaveAtLeastSize(1)
    }
})
