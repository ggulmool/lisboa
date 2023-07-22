package me.ggulmool.lisboa.adapter.db.entity.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZonedDateTime

@MappedSuperclass
abstract class BaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP COMMENT '생성일시'")
    private var createdAt: ZonedDateTime? = null

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP COMMENT '변경일시'")
    private var modifiedAt: ZonedDateTime? = null

    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
        modifiedAt = ZonedDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        modifiedAt = ZonedDateTime.now()
    }
}