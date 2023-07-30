package me.ggulmool.lisboa.out.adapter.db.entity.profits

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface QuarterProfitsRepository: JpaRepository<QuarterProfitsEntity, Long> {

    @Query(
        """
            select quarter_profits from QuarterProfitsEntity quarter_profits 
            join quarter_profits.stockEntity stock
            where stock.stockNo = :stockNo
            and quarter_profits.year = :year
            and quarter_profits.quarter = :quarter
        """
    )
    fun findByStockNoAndYearAndQuarter(@Param("stockNo") stockNo: String, @Param("year") year: String, @Param("quarter") quarter: String): QuarterProfitsEntity?

    @Query(
        """
            select new me.ggulmool.lisboa.out.adapter.db.entity.profits.QuarterProfitsDto(stock.stockNo, quarter_profits.year, quarter_profits.quarter, quarter_profits.profits) from QuarterProfitsEntity quarter_profits 
            join quarter_profits.stockEntity stock
            where stock.stockNo = :stockNo
        """
    )
    fun findQuarterProfitsByQuery(@Param("stockNo") stockNo: String): List<QuarterProfitsDto>

    @Query(
        """
            select new me.ggulmool.lisboa.out.adapter.db.entity.profits.QuarterProfitsDto(stock.stockNo, quarter_profits.year, quarter_profits.quarter, quarter_profits.profits) from QuarterProfitsEntity quarter_profits 
            join quarter_profits.stockEntity stock
            join stock.sectorEntity sector
            where sector.sectorNo = :sectorNo
        """
    )
    fun findQuarterProfitsBySectorNoQuery(@Param("sectorNo") sectorNo: String): List<QuarterProfitsDto>
}

data class QuarterProfitsDto(
    val stockNo: String,
    val year: String,
    val quarter: String,
    val profits: BigDecimal
)