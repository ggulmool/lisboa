package me.ggulmool.lisboa.domain.stock

import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.common.Quarter
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import mu.KotlinLogging
import java.math.BigDecimal
import java.math.RoundingMode

class Stock(
    var stockNo: String,
    var marketType: MarketType,
    var name: String,
    var currentPrice: Money,
    var stockQuantity: StockQuantity,
    var sector: Sector,
    var sales: Sales? = null,
    var yearProfits: YearProfits,
    var quarterProfits: QuarterProfits,
    var description: String
) {
    private val logger = KotlinLogging.logger {}

    fun isActiveMarketTypes(): Boolean {
        return marketType == MarketType.KOSPI || marketType == MarketType.KOSDAQ
    }

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
        return (targetMarketCap.price.divide(marketCap.price, 2, RoundingMode.CEILING))
            .minus(BigDecimal.ONE)
            .multiply(BigDecimal("100"))
            .setScale(1, RoundingMode.DOWN)
    }

    /**
     * 목표주가 구하기
     */
    fun targetPrice(year: String): Money {
        val targetMarketCapitalization = calculateTargetMarketCapitalization(year).price
        return Money(targetMarketCapitalization.divide(stockQuantity.value,0, RoundingMode.CEILING))
    }

    /**
     * 연간 영업이익 YoY = ((올해년도 영업이익 / 전년 영업이익) - 1.0) * 100
     */
    fun yoy(year: String): String {
        return try {
            yearProfits.yoy(year)
        } catch (e: Exception) {
            logger.warn(e) {"$stockNo 종목 yoy 계산중 오류 발생"}
            "0"
        }
    }

    /**
     * 분기 영업이익 QoQ ((현분기 영업이익 / 직전분기 영업이익) - 1.0) * 100
     */
    fun qoq(year: String, currentQuarter: Quarter, previousQuarter: Quarter): String {
        return try {
            quarterProfits.qoq(year, currentQuarter, previousQuarter)
        } catch (e: Exception) {
            logger.warn(e) {"$stockNo 종목 qoq 계산중 오류 발생"}
            "0"
        }
    }
}