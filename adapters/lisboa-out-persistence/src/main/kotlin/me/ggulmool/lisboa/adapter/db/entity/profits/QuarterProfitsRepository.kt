package me.ggulmool.lisboa.adapter.db.entity.profits

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface QuarterProfitsRepository: JpaRepository<QuarterProfitsEntity, Long> {

    @Query(
        """
            select new me.ggulmool.lisboa.adapter.db.entity.profits.QuarterProfitsDto(quarter_profits.year, quarter_profits.quarter, quarter_profits.profits) from QuarterProfitsEntity quarter_profits 
            join quarter_profits.stockEntity stock
            where stock.stockNo = :stockNo
        """
    )
    fun findQuarterProfitsByQuery(@Param("stockNo") stockNo: String): List<QuarterProfitsDto>
}

data class QuarterProfitsDto(
    val year: String,
    val quarter: String,
    val profits: BigDecimal
)