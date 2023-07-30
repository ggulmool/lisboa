package me.ggulmool.lisboa.application.port.out.stock

import me.ggulmool.lisboa.domain.stock.Stock

interface SaveStockPort {

    fun saveStock(stock: Stock)
}