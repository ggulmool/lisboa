package me.ggulmool.lisboa.out.adapter.db.entity.test

import org.springframework.data.jpa.repository.JpaRepository

interface BusinessInfoRepository: JpaRepository<BusinessEntity, BusinessId> {

    fun findByUidAndBisno(uid: String, bisno: String): BusinessEntity?

    fun findByUidOrderByModifiedAtDesc(uid: String): List<BusinessEntity>

    fun findByUidOrderBySeqAsc(uid: String): List<BusinessEntity>
}