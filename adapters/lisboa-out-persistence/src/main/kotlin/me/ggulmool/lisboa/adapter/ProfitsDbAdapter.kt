package me.ggulmool.lisboa.adapter

import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsEntity
import me.ggulmool.lisboa.adapter.db.entity.profits.YearProfitsRepository
import me.ggulmool.lisboa.adapter.db.entity.stock.StockRepository
import me.ggulmool.lisboa.application.port.out.profits.SaveProfitsPort
import me.ggulmool.lisboa.domain.stock.Stock
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ProfitsDbAdapter(
    private val stockRepository: StockRepository,
    private val yearProfitsRepository: YearProfitsRepository,
): SaveProfitsPort {

    @Transactional
    override fun saveYearProfits(stock: Stock) {

        if (!stock.hasYearProfits()) {
            throw IllegalStateException("${stock.stockNo} 종목의 연간이익 정보가 없습니다.")
        }

        val findStockEntity = stockRepository.findByStockNo(stock.stockNo) ?: throw IllegalStateException("저장된 종목의 주식 정보가 없습니다.")

        stock.yearProfits.yearProfitsMap.forEach { entry ->
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
}