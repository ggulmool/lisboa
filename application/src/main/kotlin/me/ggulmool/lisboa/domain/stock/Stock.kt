package me.ggulmool.lisboa.domain.stock

import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.common.Quarter
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import java.math.BigDecimal

class Stock(
    var stockNo: String,
    var marketType: MarketType,
    var name: String,
    var currentPrice: Money,
    var stockQuantity: StockQuantity,
    var sector: Sector,
    var sales: Sales?,
    var yearProfits: YearProfits,
    var quarterProfits: QuarterProfits,
    var description: String?
) {

    fun hasYearProfits(): Boolean {
        return yearProfits.hasProfits()
    }

    fun hasQuarterProfits(): Boolean {
        return quarterProfits.hasProfits()
    }

    /**
     * 시가총액 = 현재가 * 상장주식수
     */
    fun calculateMarketCapitalization(): Money =
        Money(currentPrice.price.multiply(stockQuantity.value))

    fun calculateMarketCapitalization(currentPrice: Money, stockQuantity: StockQuantity): Money =
        Money(currentPrice.price.multiply(stockQuantity.value))

    /**
    목표시총 = 영업이익 * 멀티플
    1. 당해년도 예상 영업이익
    2. 직전 두분기 영업이익 + (다음분기 예상 영업이익 * 2)
    3. (직전 두분기 * 2)
     */
    fun calculateTargetMarketCapitalization(year: String): Money {
        return calculateTargetMarketCapitalization(yearProfits, quarterProfits, year)
    }

    private fun calculateTargetMarketCapitalization(
        yearProfits: YearProfits,
        quarterProfits: QuarterProfits,
        year: String
    ): Money {
        val profit = yearProfits.profit(year) ?: quarterProfits.profit(year)
        return profit?.multiply(BigDecimal(sector.multiple)) ?: Money("0")
    }

    /**
     *  상승여력 = ((목표시총 / 시총) - 1.0) * 100
     */
    fun increaseSpareCapacity(targetMarketCap: Money, marketCap: Money): BigDecimal {
        return (targetMarketCap.price.divide(marketCap.price, 2, BigDecimal.ROUND_CEILING))
            .minus(BigDecimal.ONE)
            .multiply(BigDecimal("100"))
            .setScale(1, BigDecimal.ROUND_DOWN)
    }

    /**
     * 목표주가 구하기
     */
    fun targetPrice(year: String): Money {
        val targetMarketCapitalization = calculateTargetMarketCapitalization(year).price
        return Money(targetMarketCapitalization.divide(stockQuantity.value,0, BigDecimal.ROUND_CEILING))
    }

    /**
     * 연간 영업이익 YoY = ((올해년도 영업이익 / 전년 영업이익) - 1.0) * 100
     */
    fun yoy(year: String): String {
        return yearProfits.yoy(year)
    }

    /**
     * 분기 영업이익 QoQ ((현분기 영업이익 / 직전분기 영업이익) - 1.0) * 100
     */
    fun qoq(year: String, currentQuarter: Quarter, previousQuarter: Quarter): String {
        return quarterProfits.qoq(year, currentQuarter, previousQuarter)
    }
}