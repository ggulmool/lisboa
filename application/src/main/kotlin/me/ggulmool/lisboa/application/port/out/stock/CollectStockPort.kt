package me.ggulmool.lisboa.application.port.out.stock

import me.ggulmool.lisboa.domain.stock.StockCode

interface CollectStockPort {

    fun getAll(): List<StockCode>
}