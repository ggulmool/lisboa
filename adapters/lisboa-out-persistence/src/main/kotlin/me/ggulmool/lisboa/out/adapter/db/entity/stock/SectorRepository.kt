package me.ggulmool.lisboa.out.adapter.db.entity.stock

import org.springframework.data.jpa.repository.JpaRepository

interface SectorRepository: JpaRepository<SectorEntity, Long> {

    fun findBySectorNo(sectorNo: String): SectorEntity?
}