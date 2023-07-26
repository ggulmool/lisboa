package me.ggulmool.lisboa.adapter.db.entity.stock

import jakarta.persistence.*
import me.ggulmool.lisboa.adapter.db.entity.common.BaseEntity
import me.ggulmool.lisboa.domain.stock.Sector


@Table(name = "stock_sector_m")
@Entity
class SectorEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sector_id")
    val sectorId: Long?,

    @Column(name = "sector_no")
    var sectorNo: String,

    @Column(name = "sector_name")
    var sectorName: String,

    @Column(name = "multiple")
    var multiple: Int,
): BaseEntity() {

    fun update(sector: Sector) {
        this.sectorNo = sector.no
        this.sectorName = sector.industryName
        this.multiple = sector.multiple
    }
}