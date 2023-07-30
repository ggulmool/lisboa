package me.ggulmool.lisboa.out.adapter.db.entity.profits

import jakarta.persistence.*
import me.ggulmool.lisboa.out.adapter.db.entity.stock.StockEntity
import java.math.BigDecimal

/**
 * 종목별 연간 영업이익
 */
@Table(name = "stock_profits_year_l")
@Entity
class YearProfitsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_year_profits_id")
    val stockYearProfitsId: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    val stockEntity: StockEntity,

    @Column(name = "year")
    val year: String,

    @Column(name = "profits")
    var profits: BigDecimal,
) {
    fun update(profits: BigDecimal) {
        this.profits = profits
    }
}