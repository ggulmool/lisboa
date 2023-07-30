package me.ggulmool.lisboa.`in`.adapter.web

import me.ggulmool.lisboa.application.port.`in`.GetStockQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/stock")
class StockController(
    private val getStockQuery: GetStockQuery
) {

    @GetMapping
    fun getStock(@RequestParam("stock_no") stockNo: String): GetStockQuery.StockPresentation {
        return getStockQuery.getStock(stockNo)
    }

    @GetMapping("/sector")
    fun getStockBySector(@RequestParam("sector_no") sectorNo: String): List<GetStockQuery.StockPresentation> {
        return getStockQuery.getStocksBySectorNo(sectorNo)
    }
}