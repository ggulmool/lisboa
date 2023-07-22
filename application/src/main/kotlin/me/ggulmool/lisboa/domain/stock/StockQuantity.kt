package me.ggulmool.lisboa.domain.stock

import me.ggulmool.lisboa.domain.common.StringUtil
import java.math.BigDecimal

class StockQuantity(
    val value: BigDecimal
) {
    constructor(value: String): this(BigDecimal(StringUtil.removeComma(value)))
}
