package me.ggulmool.lisboa.adapter.db

import me.ggulmool.lisboa.adapter.db.entity.profits.QuarterProfitsDto
import me.ggulmool.lisboa.adapter.db.entity.profits.QuarterProfitsRepository
import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsDto
import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsRepository
import me.ggulmool.lisboa.adapter.db.entity.stock.StockRepository
import me.ggulmool.lisboa.application.port.out.stock.LoadStockPort
import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.common.Quarter
import me.ggulmool.lisboa.domain.profits.QuarterProfit
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import me.ggulmool.lisboa.domain.stock.MarketType
import me.ggulmool.lisboa.domain.stock.Sector
import me.ggulmool.lisboa.domain.stock.Stock
import me.ggulmool.lisboa.domain.stock.StockQuantity
import org.springframework.stereotype.Component

@Component
class LoadStockProfitsDbAdapter(
    private val stockRepository: StockRepository,
    private val quarterProfitsRepository: QuarterProfitsRepository,
    private val yearProfitsRepository: YearProfitsRepository,
): LoadStockPort {

    override fun loadStock(stockNo: String): Stock {
        val findByStock = stockRepository.findStockByQuery(stockNo) ?: throw IllegalStateException("존재하지 않는 종목입니다. $stockNo")

        val findYearProfits = yearProfitsRepository.findYearProfitsByQuery(stockNo)
        val findQuarterProfits = quarterProfitsRepository.findQuarterProfitsByQuery(stockNo)
        return Stock(
            stockNo = stockNo,
            marketType = MarketType[findByStock.market],
            name = findByStock.stockName,
            currentPrice = Money(findByStock.currentPrice),
            stockQuantity = StockQuantity(findByStock.stockQuantity),
            sector = Sector[findByStock.sectorEntity.sectorNo],
            yearProfits = mapToYearProfits(findYearProfits),
            quarterProfits = mapToQuarterProfits(findQuarterProfits),
            description = findByStock.description
        )
    }

    private fun mapToYearProfits(yearProfits: List<YearProfitsDto>): YearProfits {
        return YearProfits(
            yearProfits.associateBy(
                {it.year}, {Money(it.profits)}
            ).toMutableMap())
    }

    private fun mapToQuarterProfits(quarterProfits: List<QuarterProfitsDto>): QuarterProfits {
        val profits = QuarterProfits()

        quarterProfits.groupBy { it.year }
            .forEach { g ->
                profits.addQuarterProfit(
                    g.key,
                    QuarterProfit(g.value.associateBy({ Quarter[it.quarter] }, { Money(it.profits) }).toMutableMap())
                )
            }

        return profits
    }
}