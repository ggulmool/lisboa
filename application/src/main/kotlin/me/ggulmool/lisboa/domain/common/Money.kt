package me.ggulmool.lisboa.domain.common

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

data class Money(
    var price: BigDecimal
) {
    constructor(value: String): this(BigDecimal(StringUtil.removeComma(value)))

    fun add(adder: BigDecimal): Money {
        return Money(price.add(adder))
    }

    fun add(adder: Money): Money {
        return Money(price.add(adder.price))
    }

    fun multiply(multiplier: BigDecimal): Money {
        return Money(price.multiply(multiplier))
    }

    fun multiply(multiplier: Money): Money {
        return Money(price.multiply(multiplier.price))
    }

    fun toBillion(): Money {
        return Money(price).multiply(Money("100,000,000"))
    }

    fun billionFormat(): String {
        return billionFormat(price)
    }

    fun billionFormat(value: BigDecimal): String {
        return if (value < BigDecimal.ZERO) {
            "-${abbreviate(value.multiply(BigDecimal("-1")))}"
        } else {
            abbreviate(value)
        }
    }

    private fun abbreviate(value: BigDecimal): String {
        val money = value.times(HUNDRED).divide(BILLION).times(BigDecimal(0.01)).setScale(0, RoundingMode.HALF_EVEN)
        return moneyFormat(money)
    }

    fun moneyFormat(): String {
        return moneyFormat(price)
    }

    fun moneyFormat(value: BigDecimal): String {
        val df = DecimalFormat("#,##0").apply {
            groupingSize = 3
            decimalFormatSymbols = DecimalFormatSymbols().apply { groupingSeparator = ',' }
        }
        return df.format(value)
    }

    override fun toString(): String {
        return moneyFormat()
    }

    companion object {
        val HUNDRED = BigDecimal("100")
        val TEN_THOUSAND = BigDecimal("10000")
        val BILLION = BigDecimal("100000000")
        val TRILLION = BigDecimal("1000000000000")
    }
}