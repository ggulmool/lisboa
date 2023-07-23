package me.ggulmool.lisboa.adapter.db.entity

import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository: JpaRepository<StockEntity, Long> {

    fun findByStockNo(stockNo: String): StockEntity?
}