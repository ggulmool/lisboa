package me.ggulmool.lisboa.application.service

import me.ggulmool.lisboa.application.port.`in`.GetStockQuery
import me.ggulmool.lisboa.application.port.out.stock.LoadStockPort
import me.ggulmool.lisboa.domain.common.Quarter
import me.ggulmool.lisboa.domain.stock.Stock
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class StockService(
    private val loadStockPort: LoadStockPort,
) : GetStockQuery {

    private val logger = KotlinLogging.logger {}

    override fun getStock(stockNo: String): GetStockQuery.StockPresentation {
        val stock = loadStockPort.loadStock(stockNo)

        return mapToStockInfo(stock)
    }

    override fun getStocksBySectorNo(sectorNo: String): List<GetStockQuery.StockPresentation> {
        return loadStockPort.loadStocks(sectorNo)
            .map { mapToStockInfo(it) }
            .sortedByDescending {
                it.increaseSpareCapacity
            }
    }

    override fun getRankStockNos(): List<String> {
        return loadStockPort.loadStocks()
            .map { mapToStockInfo(it) }
            .sortedByDescending {
                it.increaseSpareCapacity
            }
            .map { it.code }
    }

    private fun mapToStockInfo(stock: Stock): GetStockQuery.StockPresentation {
        val marketCapitalization = stock.calculateMarketCapitalization()
        val targetMarketCapitalization = stock.calculateTargetMarketCapitalization("2023")

        return GetStockQuery.StockPresentation(
            code = stock.stockNo,
            name = stock.name,
            market = stock.marketType.market,
            sector = stock.sector.industryName,
            year1 = Pair("2021.12", stock.yearProfits.profit("2021")?.billionFormat() ?: ""),
            year2 = Pair("2022.12", stock.yearProfits.profit("2022")?.billionFormat() ?: ""),
            year3 = Pair("2023.12", stock.yearProfits.profit("2023")?.billionFormat() ?: ""),
            yoy = stock.yoy("2023"),
            quarter1 = Pair("2022.06", stock.quarterProfits.profit("2022", Quarter.SECOND)?.billionFormat() ?: ""),
            quarter2 = Pair("2022.09", stock.quarterProfits.profit("2022", Quarter.THIRD)?.billionFormat() ?: ""),
            quarter3 = Pair("2022.12", stock.quarterProfits.profit("2022", Quarter.FOURTH)?.billionFormat() ?: ""),
            quarter4 = Pair("2023.03", stock.quarterProfits.profit("2023", Quarter.FIRST)?.billionFormat() ?: ""),
            quarter5 = Pair("2023.06", stock.quarterProfits.profit("2023", Quarter.SECOND)?.billionFormat() ?: ""),
            qoq = stock.qoq("2023", Quarter.SECOND, Quarter.FIRST),
            marketCapitalization = marketCapitalization.billionFormat(),
            targetMarketCapitalization = targetMarketCapitalization.billionFormat(),
            increaseSpareCapacity = stock.increaseSpareCapacity(
                targetMarketCapitalization,
                marketCapitalization
            ),
            multiple = stock.sector.multiple,
            currentPrice = stock.currentPrice.moneyFormat(),
            targetCurrentPrice = stock.targetPrice("2023").moneyFormat(),
            description = stock.description
        )
    }
}