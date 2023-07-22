package me.ggulmool.lisboa.application.port.out

import me.ggulmool.lisboa.domain.stock.StockCode

interface StockListPort {

    fun getAll(): List<StockCode>
}