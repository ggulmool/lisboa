package me.ggulmool.lisboa.out.adapter.db.entity.stock

import jakarta.persistence.*
import me.ggulmool.lisboa.out.adapter.db.entity.common.BaseEntity
import me.ggulmool.lisboa.domain.stock.Stock
import me.ggulmool.lisboa.domain.stock.StockQuantity
import java.math.BigDecimal

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
    var sectorEntity: SectorEntity,

    @Column(name = "stock_market")
    var market: String,

    @Column(name = "current_price")
    var currentPrice: BigDecimal,

    @Column(name = "quantity")
    var stockQuantity: BigDecimal,

    @Column(name = "stock_description")
    var description: String,

    ): BaseEntity() {

    fun updateStockInfo(stock: Stock, sectorEntity: SectorEntity) {
        this.stockName = stock.name
        this.sectorEntity = sectorEntity
        this.market = stock.marketType.market
        this.description = stock.description
        this.currentPrice = stock.currentPrice.price
        this.stockQuantity = stock.stockQuantity.value
    }
}