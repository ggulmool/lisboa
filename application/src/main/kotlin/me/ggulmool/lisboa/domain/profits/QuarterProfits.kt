package me.ggulmool.lisboa.domain.profits

import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.common.Quarter
import me.ggulmool.lisboa.domain.common.StringUtil
import java.math.BigDecimal

class QuarterProfits(
    val profitsMap: MutableMap<String, QuarterProfit> = mutableMapOf()
) {
    fun hasProfits(): Boolean {
        return profitsMap.keys.size > 0
    }

    fun addQuarterProfit(year: String, quarterProfit: QuarterProfit) {
        profitsMap[year] = quarterProfit
    }

    fun addQuarterProfit(year: String, quarter: Quarter, money: Money) {
        addYear(year)
        profitsMap[year]!!.put(quarter, money)
    }

    private fun addYear(year: String) {
        if (profitsMap[year] == null) {
            profitsMap[year] = QuarterProfit(mutableMapOf())
        }
    }

    fun profit(year: String): Money? {
        return profitsMap[year] ?.calculateProfit()
    }

    fun profit(year: String, quarter: Quarter): Money? {
        val profits = getYearProfits(year)
        return profits?.profit(quarter)
    }

    private fun getYearProfits(year: String): QuarterProfit? {
        return profitsMap[year]
    }

    fun qoq(year: String, currentQuarter: Quarter, previousQuarter: Quarter): String {
        val currentQuarterProfit = profit(year, currentQuarter)
        val previousQuarterProfit = profit(year, previousQuarter)

        if (currentQuarterProfit != null && previousQuarterProfit != null) {
            if ((currentQuarterProfit.price > BigDecimal.ZERO) && (previousQuarterProfit.price < BigDecimal.ZERO)) {
                return "흑전"
            }

            if ((currentQuarterProfit.price < BigDecimal.ZERO) && (previousQuarterProfit.price < BigDecimal.ZERO)) {
                return "적지"
            }

            if (previousQuarterProfit.price == BigDecimal.ZERO) {
                return StringUtil.EMPTY
            }

            return (currentQuarterProfit.price.divide(previousQuarterProfit.price, 2, BigDecimal.ROUND_CEILING))
                .minus(BigDecimal.ONE)
                .multiply(BigDecimal("100"))
                .setScale(1, BigDecimal.ROUND_DOWN).toString()
        }

        return StringUtil.EMPTY
    }
}