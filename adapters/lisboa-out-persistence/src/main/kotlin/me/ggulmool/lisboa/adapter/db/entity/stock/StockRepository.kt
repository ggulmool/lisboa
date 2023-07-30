package me.ggulmool.lisboa.adapter.db.entity.stock

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StockRepository: JpaRepository<StockEntity, Long> {

    fun findByStockNo(stockNo: String): StockEntity?

    @Query(
        """
            select stock, sector from StockEntity stock 
            join stock.sectorEntity sector
            where stock.stockNo = :stockNo
        """
    )
    fun findStockByQuery(@Param("stockNo") stockNo: String): StockEntity?
}