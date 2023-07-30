package me.ggulmool.lisboa.application.port.`in`

interface GetStockQuery {

    fun getStock(stockNo: String): StockPresentation

    data class StockPresentation(
        val code: String,       // 종목코드
        val name: String,       // 종목명
        val market: String,     // 시장
        val sector: String,     // 섹터
        val year1: Pair<String, String>,      // 2021.12
        val year2: Pair<String, String>,      // 2022.12
        val year3: Pair<String, String>,      // 2023.12
        val yoy: String,        // YoY(%)
        val quarter1: Pair<String, String>,   // 2022.06
        val quarter2: Pair<String, String>,   // 2022.09
        val quarter3: Pair<String, String>,   // 2022.12
        val quarter4: Pair<String, String>,   // 2023.03
        val quarter5: Pair<String, String>,   // 2023.06
        val qoq: String,        // QoQ(%)
        val marketCapitalization: String,       // 현시총(억)
        val targetMarketCapitalization: String, // 목표시총(억)
        val increaseSpareCapacity: String,      // 상승여력
        val multiple: Int,                      // 멀티플	업종
        val currentPrice: String,               // 현재가(원)
        val targetCurrentPrice: String,         // 목표가(원)
        val description: String,                // 기업설명
    )
}