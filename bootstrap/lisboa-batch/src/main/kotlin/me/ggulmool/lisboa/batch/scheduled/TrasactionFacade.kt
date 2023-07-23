package me.ggulmool.lisboa.batch.scheduled

import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import me.ggulmool.lisboa.domain.stock.StockCode
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TrasactionFacade(
    private val saveStockPort: SaveStockPort,
) {

    @Transactional
    fun trans(i: Int) {
        saveStockPort.saveStock(StockCode("$i", "hello+$i"))
        externalApi(i)
    }

    fun externalApi(i: Int) {
        if (i == 3) {
            throw IllegalStateException("failed..")
        }
    }
}