package me.ggulmool.lisboa.application.service.test

import me.ggulmool.lisboa.application.port.out.stock.ParseStockPort
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import me.ggulmool.lisboa.domain.stock.MarketType
import me.ggulmool.lisboa.domain.stock.Sector
import me.ggulmool.lisboa.domain.stock.Stock
import me.ggulmool.lisboa.domain.stock.StockQuantity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockSaveService(
    private val saveStockPort: SaveStockPort,
    private val parseStockPort: ParseStockPort,
) {
    @Transactional
    fun saveStock(stockNo: String) {
        saveStockPort.saveStock(
            Stock(
                stockNo = stockNo,
                marketType = MarketType.KOSPI,
                name = "name${stockNo}",
                currentPrice = Money("1000"),
                stockQuantity = StockQuantity("10"),
                sector = Sector.ADVERTISING,
                yearProfits = YearProfits(),
                quarterProfits = QuarterProfits(),
                description = "description"
            )
        )

        val parse = parseStockPort.parse(stockNo)
        println(parse)
    }
}