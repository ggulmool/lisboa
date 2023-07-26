package me.ggulmool.lisboa.batch.scheduled

import me.ggulmool.lisboa.application.port.out.profits.SaveProfitsPort
import me.ggulmool.lisboa.application.port.out.stock.CollectStockPort
import me.ggulmool.lisboa.application.port.out.stock.ParseStockPort
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class InstantScheduler(
    private val collectStockPort: CollectStockPort,
    private val parseStockPort: ParseStockPort,
    private val saveStockPort: SaveStockPort,
    private val saveProfitsPort: SaveProfitsPort,
): ApplicationRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(args: ApplicationArguments) {
        collectStockInfos()
//        collectDetailStockInfo("035420")
    }

    fun collectStockInfos() {
        collectStockPort.getStockList().forEach {
            try {
                if (it.no == "035420") {
                    val stock = parseStockPort.parse(it.no)
                    saveStockPort.saveSector(stock.sector)
                    saveStockPort.saveStock(stock)
                    saveProfitsPort.saveYearProfits(stock)
                }
            } catch (e: Exception) {
                logger.info { "${it.name} + ${it.no}" }
            }
        }
    }
}