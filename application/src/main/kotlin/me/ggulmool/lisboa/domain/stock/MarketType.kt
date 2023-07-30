package me.ggulmool.lisboa.domain.stock

enum class MarketType(
    val market: String
) {

    KOSPI("코스피"),
    KOSDAQ("코스닥"),
    KONEX("코넥스"),
    NONE("")
    ;

    companion object {
        operator fun get(market: String): MarketType {
            return MarketType.values().find { it.market == market } ?: throw IllegalArgumentException("잘못된 MarketType 값입니다. : $market")
        }
    }
}
