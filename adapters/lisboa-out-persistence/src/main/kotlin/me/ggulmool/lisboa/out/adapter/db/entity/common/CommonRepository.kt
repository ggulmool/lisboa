package me.ggulmool.lisboa.out.adapter.db.entity.common

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id)?: throw NoSuchElementException("저장된 내역이 없습니다. id = $id")
}