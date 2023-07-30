package me.ggulmool.lisboa.out.adapter.db

import me.ggulmool.lisboa.out.adapter.db.entity.stock.SectorEntity
import me.ggulmool.lisboa.out.adapter.db.entity.stock.SectorRepository
import me.ggulmool.lisboa.out.adapter.db.entity.stock.StockEntity
import me.ggulmool.lisboa.out.adapter.db.entity.stock.StockRepository
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import me.ggulmool.lisboa.domain.stock.Stock
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StockDbAdapter(
    private val stockRepository: StockRepository,
    private val sectorRepository: SectorRepository,
) : SaveStockPort {

    @Transactional
    override fun saveStock(stock: Stock) {
        val savedStockEntity = stockRepository.findStockByQuery(stock.stockNo)

        if (savedStockEntity != null) {
            savedStockEntity.updateStockInfo(stock, savedStockEntity.sectorEntity)
        } else {
            val savedSectorEntity = sectorRepository.findBySectorNo(stock.sector.no)

            val sectorEntity = savedSectorEntity?.update(stock.sector)
                ?: sectorRepository.save(
                    SectorEntity(
                        sectorId = null,
                        sectorNo = stock.sector.no,
                        sectorName = stock.sector.industryName,
                        multiple = stock.sector.multiple
                    )
                )

            stockRepository.save(
                StockEntity(
                    stockId = null,
                    stockNo = stock.stockNo,
                    stockName = stock.name,
                    sectorEntity = sectorEntity,
                    market = stock.marketType.market,
                    currentPrice = stock.currentPrice.price,
                    stockQuantity = stock.stockQuantity.value,
                    description = stock.description
                )
            )
        }
    }
}