package me.ggulmool.application.domain.stock

import me.ggulmool.application.domain.common.Money
import me.ggulmool.application.domain.common.Quarter
import java.math.BigDecimal

class Profits(
    private var profits: MutableMap<Quarter, Money>
) {
    fun put(quarter: Quarter, money: Money) {
        profits[quarter] = money
    }

    fun calculateProfit(): Money? {
        return when {
            profits.containsKey(Quarter.FOURTH) -> {
                profits.getOrElse(Quarter.FOURTH) { Money("0") }
                    .add(profits.getOrElse(Quarter.THIRD) { Money("0") })
                    .add(profits.getOrElse(Quarter.SECOND) { Money("0") })
                    .add(profits.getOrElse(Quarter.FIRST) { Money("0") })
            }

            profits.containsKey(Quarter.THIRD) -> {
                profits.getOrElse(Quarter.THIRD) { Money("0") }.multiply(BigDecimal("2"))
                    .add(profits.getOrElse(Quarter.SECOND) { Money("0") })
                    .add(profits.getOrElse(Quarter.FIRST) { Money("0") })
            }

            profits.containsKey(Quarter.SECOND) -> {
                profits.getOrElse(Quarter.SECOND) { Money("0") }
                    .add(profits.getOrElse(Quarter.FIRST) { Money("0") })
                    .multiply(BigDecimal("2"))
            }

            profits.containsKey(Quarter.FIRST) -> {
                profits.getOrElse(Quarter.FIRST) { Money("0") }
                    .multiply(BigDecimal("4"))
            }
            else -> {
                return null
            }
        }
    }

    fun profit(quarter: Quarter): Money? {
        return profits[quarter]
    }
}