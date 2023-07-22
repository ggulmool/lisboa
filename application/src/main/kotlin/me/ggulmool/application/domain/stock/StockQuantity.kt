package me.ggulmool.application.domain.stock

import me.ggulmool.application.domain.common.StringUtil
import java.math.BigDecimal

class StockQuantity(
    val value: BigDecimal
) {
    constructor(value: String): this(BigDecimal(StringUtil.removeComma(value)))
}
