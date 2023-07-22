package me.ggulmool.lisboa.application.port.out.stock

import me.ggulmool.lisboa.domain.stock.MarketType
import me.ggulmool.lisboa.domain.stock.Stock

interface ParseStockPort {

    fun parse(stockNo: String, marketType: MarketType): Stock
}