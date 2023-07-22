package me.ggulmool.application.domain.stock

import me.ggulmool.application.domain.common.Money
import me.ggulmool.application.domain.common.StringUtil
import java.math.BigDecimal

/**
 * 연간 영업이익
 */
class YearProfits(
    yearProfitsMap: MutableMap<String, Money>
) {
    private var yearProfitsMap = mutableMapOf<String, Money>()

    init {
        this.yearProfitsMap = yearProfitsMap
    }

    fun addProfit(year: String, money: Money) {
        yearProfitsMap[year] = money
    }

    fun profit(year: String): Money? {
        return yearProfitsMap[year]
    }

    fun yoy(year: String): String {
        val currentYearProfit = profit(year)
        val previousYear = BigDecimal(year).minus(BigDecimal.ONE).toString()
        val previousYearProfit = profit(previousYear)

        if (currentYearProfit != null && previousYearProfit != null) {
            if ((currentYearProfit.price > BigDecimal.ZERO) && (previousYearProfit.price < BigDecimal.ZERO)) {
                return "흑전"
            }

            if ((currentYearProfit.price < BigDecimal.ZERO) && (previousYearProfit.price < BigDecimal.ZERO)) {
                return "적지"
            }

            if (previousYearProfit.price == BigDecimal.ZERO) {
                return StringUtil.EMPTY
            }

            return (currentYearProfit.price.divide(previousYearProfit.price, 2, BigDecimal.ROUND_CEILING))
                .minus(BigDecimal.ONE)
                .multiply(BigDecimal("100"))
                .setScale(1, BigDecimal.ROUND_DOWN).toString()
        }

        return StringUtil.EMPTY
    }
}
