package me.ggulmool.lisboa.domain.common

enum class Quarter(
    val quarterName: String,
    val quarterSet: Set<String>,
) {

    FIRST("03", setOf("01","02","03")),
    SECOND("06", setOf("04","05","06")),
    THIRD("09", setOf("07","08","09")),
    FOURTH("12", setOf("10","11","12"))
    ;

    companion object {
        operator fun get(month: String): Quarter {
            return values().find { it.quarterSet.contains(month) } ?: throw IllegalArgumentException("$month - 잘못된 Quarter 값입니다.")
        }
    }
}