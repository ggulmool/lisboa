package me.ggulmool.lisboa.adapter.db.entity

import jakarta.persistence.*
import me.ggulmool.lisboa.adapter.db.entity.common.BaseEntity

@Table(name = "stock")
@Entity
class StockEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    val stockId: Long?,

    @Column(name = "stock_no")
    var stockNo: String,

    @Column(name = "stock_name")
    var stockName: String,

): BaseEntity() {

    fun updateStockName(name: String) {
        stockName = name
    }
}