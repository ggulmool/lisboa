package me.ggulmool.lisboa.adapter.db.entity.stock

import jakarta.persistence.*
import me.ggulmool.lisboa.adapter.db.entity.common.BaseEntity
import me.ggulmool.lisboa.domain.stock.Stock

@Table(name = "stock_m")
@Entity
class StockEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    val stockId: Long?,

    @Column(name = "stock_no")
    var stockNo: String,

    @Column(name = "stock_name")
    var stockName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    var sectorEntity: SectorEntity? = null,

    @Column(name = "stock_market")
    var market: String? = null,

    @Column(name = "stock_description")
    var description: String? = null,

): BaseEntity() {

    fun updateStockInfo(stock: Stock, sectorEntity: SectorEntity) {
        this.stockName = stock.name
        this.sectorEntity = sectorEntity
        this.market = stock.marketType.name
        this.description = stock.description
    }
}