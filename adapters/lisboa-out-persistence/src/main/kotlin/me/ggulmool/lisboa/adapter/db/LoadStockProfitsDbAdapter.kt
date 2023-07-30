package me.ggulmool.lisboa.adapter.db

import me.ggulmool.lisboa.adapter.db.entity.profits.QuarterProfitsDto
import me.ggulmool.lisboa.adapter.db.entity.profits.QuarterProfitsRepository
import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsDto
import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsRepository
import me.ggulmool.lisboa.adapter.db.entity.stock.StockEntity
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

        return createStock(findByStock, findYearProfits, findQuarterProfits)
    }

    override fun loadStocks(sectorNo: String): Iterable<Stock> {
        val stocks = stockRepository.findStockBySectorNoQuery(sectorNo)
        val yearProfitsGroups = yearProfitsRepository.findYearProfitsBySectorNoQuery(sectorNo).groupBy { it.stockNo }
        val quarterProfitsGroups = quarterProfitsRepository.findQuarterProfitsBySectorNoQuery(sectorNo).groupBy { it.stockNo }

        return stocks.map {
            createStock(
                it,
                yearProfitsGroups.getValue(it.stockNo),
                quarterProfitsGroups.getValue(it.stockNo)
            )
        }
    }

    private fun createStock(
        stockEntity: StockEntity,
        yearProfitsDtos: List<YearProfitsDto>,
        quarterProfitsDtos: List<QuarterProfitsDto>,
    ): Stock {
        return Stock(
            stockNo = stockEntity.stockNo,
            marketType = MarketType[stockEntity.market],
            name = stockEntity.stockName,
            currentPrice = Money(stockEntity.currentPrice),
            stockQuantity = StockQuantity(stockEntity.stockQuantity),
            sector = Sector[stockEntity.sectorEntity.sectorNo],
            yearProfits = mapToYearProfits(yearProfitsDtos),
            quarterProfits = mapToQuarterProfits(quarterProfitsDtos),
            description = stockEntity.description
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