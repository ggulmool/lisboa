package me.ggulmool.application.domain.common

enum class Quarter(val quarterSet: Set<String>) {

    FIRST(setOf("01","02","03")),
    SECOND(setOf("04","05","06")),
    THIRD(setOf("07","08","09")),
    FOURTH(setOf("10","11","12"))
    ;

    companion object {
        operator fun get(month: String): Quarter {
            return values().find { it.quarterSet.contains(month) } ?: throw IllegalArgumentException("$month - 잘못된 Quarter 값입니다.")
        }
    }
}