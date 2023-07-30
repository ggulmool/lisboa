package me.ggulmool.lisboa.application.port.out.stock

import me.ggulmool.lisboa.domain.stock.Stock

interface LoadStockPort {

    fun loadStock(stockNo: String): Stock
}