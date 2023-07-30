package me.ggulmool.lisboa.out.adapter.parser

import me.ggulmool.lisboa.application.port.out.stock.ParseStockPort
import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.common.Quarter
import me.ggulmool.lisboa.domain.common.StringUtil
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import me.ggulmool.lisboa.domain.stock.*
import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

@Component
class NaverStockParser : ParseStockPort {

    private val logger = KotlinLogging.logger {}

    private val url: String = "https://finance.naver.com/item/main.nhn?code=%s"

    override fun parse(stockNo: String): Stock {
        val document = Jsoup.connect(String.format(url, stockNo)).get()
        val name = extractStockName(document)
        val currentPrice = extractCurrentPrice(document)
        val stockQuantity = extractStockQuantity(document)

        val marketType: MarketType = try {
            extractMarketType(document)
        } catch (e: Exception) {
            logger.warn(e) { "$stockNo: 마켓종류 데이터 추출시 오류 발생" }
            MarketType.NONE
        }

        val sector = try {
            extractSector(document)
        } catch (e: Exception) {
            logger.warn(e) { "$stockNo: 종목 섹터 데이터 추출시 오류 발생" }
            Sector.DEFAULT
        }

        val yearProfits = extractYearProfit(document)
        val quarterProfits = extractQuarterProfit(document)
        val description = extractDescription(document)

        return Stock(
            stockNo,
            marketType,
            name,
            currentPrice,
            stockQuantity,
            sector,
            null,
            yearProfits,
            quarterProfits,
            description
        )
    }

    private fun extractMarketType(document: Document): MarketType {
        val elements = getCompanyInfoTableElements(document)
        val marketType = elements.select("div.description > img").attr("alt")
        return MarketType[marketType]
    }

    private fun extractStockName(document: Document): String {
        val elements = getCompanyInfoTableElements(document)
        return elements.select("h2 > a").text()
    }

    private fun extractCurrentPrice(document: Document): Money {
        val elements = getCompanyInfoTableElements(document)
        val priceElements = elements.select("div.rate_info > div.today > p.no_today > em > span").not("span.blind")
        val currentPrice = priceElements.joinToString("") { it.text() }
        return Money(currentPrice)
    }

    private fun extractStockQuantity(document: Document): StockQuantity {
        val elements = getCompanyInfoTableElements(document)
        val stockQuantityElement =
            try {
                elements.select("div#aside").select("div.first > table > tbody > tr")[2].select("td > em")
            } catch (e: Exception) {
                logger.info { "주식수 데이터 추출시 오류 발생 fallback 처리" }
                elements.select("div#aside").select("div.first > table > tbody > tr")[1].select("td > em")
            }
        return StockQuantity(stockQuantityElement.text())
    }

    private fun extractSector(document: Document): Sector {
        val elements = getCompanyInfoTableElements(document)
        val sectorNoElement = elements.select("div.trade_compare > h4").select("em > a").attr("href")
        val sectorNo = sectorNoElement.substring(sectorNoElement.lastIndexOf("no=") + 3)
        return Sector[sectorNo]
    }

    private fun extractYearProfit(document: Document): YearProfits {
        val yearProfits = YearProfits(mutableMapOf())
        val elements: Elements = getProfitTableElements(document)
        if (elements.size > 0) {
            val yearProfitElements = elements.select("table > tbody > tr")[1].select("td")
            if (yearProfitElements.size > 0) {
                elements.select("table > thead > tr")[1].select("th")
                    .filterIndexed { index, _ -> index < 4 }
                    .forEachIndexed { index, element ->
                        if (StringUtil.isNotEmpty(yearProfitElements[index].text())) {
                            yearProfits.addProfit(
                                element.text().substring(0, 4),
                                Money(yearProfitElements[index].text()).toBillion()
                            )
                        }
                    }
            }
        }

        return yearProfits
    }

    private fun extractQuarterProfit(document: Document): QuarterProfits {
        val quarterProfits = QuarterProfits(mutableMapOf())
        val elements: Elements = getProfitTableElements(document)
        if (elements.size > 0) {
            val quarterProfitElements = elements.select("table > tbody > tr")[1].select("td")
            if (quarterProfitElements.size > 0) {
                elements.select("table > thead > tr")[1].select("th")
                    .filterIndexed { index, _ -> index > 3 }
                    .forEachIndexed { index, element ->
                        if (StringUtil.isNotEmpty(quarterProfitElements[index + 4].text())) {
                            val year = element.text().substring(0, 4)
                            val month = element.text().substring(5, 7)

//                            quarterProfits.addYear(year)
                            quarterProfits.addQuarterProfit(
                                year,
                                Quarter[month],
                                Money(quarterProfitElements[index + 4].text()).toBillion()
                            )
                        }
                    }
            }
        }

        return quarterProfits
    }

    private fun extractDescription(document: Document): String {
        val elements = getCompanyInfoTableElements(document)
        val descriptionElements = elements.select("div.summary_info > p")
        return descriptionElements.joinToString("\n") { it.text() }
    }

    private fun getCompanyInfoTableElements(document: Document): Elements {
        return document.select("div.new_totalinfo")
    }

    private fun getProfitTableElements(document: Document): Elements {
        return document.select("div.cop_analysis > div.sub_section")
    }
}