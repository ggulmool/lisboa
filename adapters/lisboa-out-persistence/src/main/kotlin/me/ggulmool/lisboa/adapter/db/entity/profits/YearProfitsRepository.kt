package me.ggulmool.lisboa.adapter.db.entity.profits

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface YearProfitsRepository: JpaRepository<YearProfitsEntity, Long> {

    @Query(
        """
            select year_profits from YearProfitsEntity year_profits 
            join year_profits.stockEntity stock
            where stock.stockNo = :stockNo
            and year_profits.year = :year
        """
    )
    fun findByStockNoAndYear(@Param("stockNo") stockNo: String, @Param("year") year: String): YearProfitsEntity?

    @Query(
        """
            select new me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsDto(stock.stockNo, year_profits.year, year_profits.profits) from YearProfitsEntity year_profits 
            join year_profits.stockEntity stock
            where stock.stockNo = :stockNo
        """
    )
    fun findYearProfitsByQuery(@Param("stockNo") stockNo: String): List<YearProfitsDto>

    @Query(
        """
            select new me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsDto(stock.stockNo, year_profits.year, year_profits.profits) from YearProfitsEntity year_profits 
            join year_profits.stockEntity stock
            join stock.sectorEntity sector
            where sector.sectorNo = :sectorNo
        """
    )
    fun findYearProfitsBySectorNoQuery(@Param("sectorNo") sectorNo: String): List<YearProfitsDto>
}

data class YearProfitsDto(
    val stockNo: String,
    val year: String,
    val profits: BigDecimal
)