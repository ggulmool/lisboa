package me.ggulmool.lisboa.adapter.collect

import me.ggulmool.lisboa.application.port.out.stock.CollectStockPort
import me.ggulmool.lisboa.domain.stock.StockCode
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class StockListCollector: CollectStockPort {
    private val url: String = "http://kind.krx.co.kr/corpgeneral/corpList.do?method=download&searchType=13"

    override fun getStockList(): Iterable<StockCode> {
        val document = Jsoup.connect(url).ignoreContentType(true).get()
        return extractStockList(document)
    }

    private fun extractStockList(document: Document): Iterable<StockCode> {
        val elements = document.select("html > body > table > tbody > tr")
        return elements
            .filterIndexed {index, _ -> index > 0 }
            .map {
                val tdSelector = it.select("td")
                StockCode(
                    no = tdSelector[1].text(),
                    name = tdSelector[0].text()
                )
            }.toSet()
    }
}