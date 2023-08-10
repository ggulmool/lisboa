package me.ggulmool.lisboa.out.adapter.db.entity.test

import jakarta.persistence.*
import me.ggulmool.lisboa.domain.test.BusinessInfo
import me.ggulmool.lisboa.out.adapter.db.entity.common.BaseEntity

@Entity
@Table(name = "business_m")
@IdClass(BusinessId::class)
class BusinessEntity(
    @Id
    @Column(name = "uid")
    val uid: String,

    @Id
    @Column(name = "bisno")
    val bisno: String,

    @Column(name = "seq")
    var seq: Int,

    @Column(name = "active_yn")
    var actYn: String,
): BaseEntity() {

    fun updateSeq(seq: Int) {
        this.seq = seq
    }
}