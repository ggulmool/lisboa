package me.ggulmool.lisboa.application.port.out

import me.ggulmool.lisboa.domain.stock.MarketType
import me.ggulmool.lisboa.domain.stock.Stock

interface StockParsePort {

    fun parse(stockNo: String, marketType: MarketType): Stock
}