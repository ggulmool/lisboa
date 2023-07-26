package me.ggulmool.lisboa.adapter.db

import me.ggulmool.lisboa.adapter.db.entity.profits.QuarterProfitsEntity
import me.ggulmool.lisboa.adapter.db.entity.profits.QuarterProfitsRepository
import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsEntity
import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsRepository
import me.ggulmool.lisboa.adapter.db.entity.stock.StockEntity
import me.ggulmool.lisboa.adapter.db.entity.stock.StockRepository
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
        val findStockEntity = stockRepository.findByStockNo(stock.stockNo) ?: throw IllegalStateException("저장된 종목의 주식 정보가 없습니다.")

        if (stock.hasYearProfits()) {
            val yearProfits = stock.yearProfits
            saveYearProfits(yearProfits, findStockEntity)
        }

        if (stock.hasYearProfits()) {
            val quarterProfits = stock.quarterProfits
            saveQuarterProfits(quarterProfits, findStockEntity)
        }
    }

    private fun saveYearProfits(yearProfits: YearProfits, findStockEntity: StockEntity) {
        yearProfits.profitsMap.forEach { entry ->
            yearProfitsRepository.save(
                YearProfitsEntity(
                    stockYearProfitsId = null,
                    stockEntity = findStockEntity,
                    year = entry.key,
                    profits = entry.value.price
                )
            )
        }
    }

    private fun saveQuarterProfits(quarterProfits: QuarterProfits, findStockEntity: StockEntity) {
        quarterProfits.profitsMap.forEach { entry ->
            val profits = entry.value.profits

            profits.forEach { quarterEntry ->
                quarterProfitsRepository.save(
                    QuarterProfitsEntity(
                        stockProfitsId = null,
                        stockEntity = findStockEntity,
                        year = entry.key,
                        quarter = quarterEntry.key.quarterName,
                        profits = quarterEntry.value.price
                    )
                )
            }
        }
    }
}