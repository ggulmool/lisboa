package me.ggulmool.lisboa.adapter.db

import me.ggulmool.lisboa.adapter.db.entity.StockEntity
import me.ggulmool.lisboa.adapter.db.entity.StockRepository
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import me.ggulmool.lisboa.domain.stock.StockCode
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StockDbAdapter(
    private val stockRepository: StockRepository,
) : SaveStockPort {

    @Transactional
    override fun saveStock(stockCode: StockCode) {
        val findStockEntity = stockRepository.findByStockNo(stockCode.no)

        if (findStockEntity != null) {
            findStockEntity.updateStockName(stockCode.name)
        } else {
            stockRepository.save(
                StockEntity(
                    stockId = null,
                    stockNo = stockCode.no,
                    stockName = stockCode.name
                )
            )
        }
    }
}