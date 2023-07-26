package me.ggulmool.lisboa.adapter.db.entity.stock

import me.ggulmool.lisboa.adapter.db.entity.stock.StockEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository: JpaRepository<StockEntity, Long> {

    fun findByStockNo(stockNo: String): StockEntity?
}