package me.ggulmool.lisboa.out.adapter.db.entity.profits

import jakarta.persistence.*
import me.ggulmool.lisboa.out.adapter.db.entity.stock.StockEntity
import java.math.BigDecimal

/**
 * 종목별 분기 영업이익
 */
@Table(name = "stock_profits_quarter_l")
@Entity
class QuarterProfitsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_quarter_profits_id")
    val stockProfitsId: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    val stockEntity: StockEntity,

    @Column(name = "profits_year")
    val year: String,

    @Column(name = "profits_quarter")
    val quarter: String,

    @Column(name = "profits")
    var profits: BigDecimal,
) {
    fun update(profits: BigDecimal) {
        this.profits = profits
    }
}