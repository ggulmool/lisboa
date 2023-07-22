package me.ggulmool.lisboa.adapter.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import me.ggulmool.lisboa.adapter.db.entity.common.BaseEntity

@Entity
class StockEntity(
    @Id
    @Column(name = "stock_no")
    val stockNo: String,

    @Column(name = "stock_name")
    val stockName: String
): BaseEntity()