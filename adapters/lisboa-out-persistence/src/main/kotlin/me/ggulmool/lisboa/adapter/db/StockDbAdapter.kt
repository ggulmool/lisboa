package me.ggulmool.lisboa.adapter.db

import me.ggulmool.lisboa.adapter.db.entity.stock.SectorEntity
import me.ggulmool.lisboa.adapter.db.entity.stock.SectorRepository
import me.ggulmool.lisboa.adapter.db.entity.stock.StockEntity
import me.ggulmool.lisboa.adapter.db.entity.stock.StockRepository
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import me.ggulmool.lisboa.domain.stock.Sector
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
        val findStockEntity = stockRepository.findByStockNo(stock.stockNo)
        val findSectorEntity = sectorRepository.findBySectorNo(stock.sector.no) ?: throw IllegalStateException("저장된 섹터 정보가 없습니다.")

        if (findStockEntity != null) {
            findStockEntity.updateStockInfo(stock, findSectorEntity)
        } else {
            stockRepository.save(
                StockEntity(
                    stockId = null,
                    stockNo = stock.stockNo,
                    stockName = stock.name,
                    sectorEntity = findSectorEntity,
                    market = stock.marketType.market,
                    currentPrice = stock.currentPrice.price,
                    stockQuantity = stock.stockQuantity.value,
                    description = stock.description
                )
            )
        }
    }

    @Transactional
    override fun saveSector(sector: Sector) {
        val findSectorEntity = sectorRepository.findBySectorNo(sector.no)

        if (findSectorEntity != null) {
            findSectorEntity.update(sector)
        } else {
            sectorRepository.save(
                SectorEntity(
                    sectorId = null,
                    sectorNo = sector.no,
                    sectorName = sector.industryName,
                    multiple = sector.multiple
                )
            )
        }
    }
}