package me.ggulmool.lisboa.out.adapter.db

import me.ggulmool.lisboa.out.adapter.db.entity.profits.QuarterProfitsEntity
import me.ggulmool.lisboa.out.adapter.db.entity.profits.QuarterProfitsRepository
import me.ggulmool.lisboa.out.adapter.db.entity.profits.YearProfitsEntity
import me.ggulmool.lisboa.out.adapter.db.entity.profits.YearProfitsRepository
import me.ggulmool.lisboa.out.adapter.db.entity.stock.StockEntity
import me.ggulmool.lisboa.out.adapter.db.entity.stock.StockRepository
import me.ggulmool.lisboa.application.port.out.profits.SaveProfitsPort
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import me.ggulmool.lisboa.domain.stock.Stock
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProfitsDbAdapter(
    private val stockRepository: StockRepository,
    private val yearProfitsRepository: YearProfitsRepository,
    private val quarterProfitsRepository: QuarterProfitsRepository,
): SaveProfitsPort {

    @Transactional
    override fun saveProfits(stock: Stock) {
        val savedStockEntity = stockRepository.findStockByQuery(stock.stockNo) ?: throw IllegalStateException("저장된 종목의 주식 정보가 없습니다.")

        if (stock.hasYearProfits()) {
            saveYearProfits(stock.yearProfits, savedStockEntity)
        }

        if (stock.hasQuarterProfits()) {
            saveQuarterProfits(stock.quarterProfits, savedStockEntity)
        }
    }

    private fun saveYearProfits(yearProfits: YearProfits, stockEntity: StockEntity) {
        yearProfits.profitsMap.forEach { entry ->
            val findByYear = yearProfitsRepository.findByStockNoAndYear(stockEntity.stockNo, entry.key)
            if (findByYear != null) {
                findByYear.update(yearProfits.profit(entry.key)!!.price)
            } else {
                yearProfitsRepository.save(
                    YearProfitsEntity(
                        stockYearProfitsId = null,
                        stockEntity = stockEntity,
                        year = entry.key,
                        profits = entry.value.price
                    )
                )
            }
        }
    }

    private fun saveQuarterProfits(quarterProfits: QuarterProfits, stockEntity: StockEntity) {
        quarterProfits.profitsMap.forEach { entry ->
            val profits = entry.value.profits

            profits.forEach { quarterEntry ->
                val findByYearAndQuarter =
                    quarterProfitsRepository.findByStockNoAndYearAndQuarter(stockEntity.stockNo, entry.key, quarterEntry.key.quarterName)

                if (findByYearAndQuarter != null) {
                    findByYearAndQuarter.update(quarterEntry.value.price)
                } else {
                    quarterProfitsRepository.save(
                        QuarterProfitsEntity(
                            stockProfitsId = null,
                            stockEntity = stockEntity,
                            year = entry.key,
                            quarter = quarterEntry.key.quarterName,
                            profits = quarterEntry.value.price
                        )
                    )
                }
            }
        }
    }
}