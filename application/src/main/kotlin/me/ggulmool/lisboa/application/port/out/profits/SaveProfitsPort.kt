package me.ggulmool.lisboa.application.port.out.profits

import me.ggulmool.lisboa.domain.stock.Stock

interface SaveProfitsPort {

    fun saveProfits(stock: Stock)
}