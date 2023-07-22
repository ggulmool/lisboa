package me.ggulmool.lisboa.adapter.db

import me.ggulmool.lisboa.adapter.db.entity.StockEntity
import me.ggulmool.lisboa.adapter.db.entity.StockRepository
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import me.ggulmool.lisboa.domain.stock.StockCode
import org.springframework.stereotype.Component

@Component
class StockDbAdapter(
    private val stockRepository: StockRepository
): SaveStockPort {

    override fun saveStock(stockCode: StockCode) {
        val stockEntity = StockEntity(
            stockNo = stockCode.no,
            stockName = stockCode.name
        )
        stockRepository.save(stockEntity)
    }
}