package me.ggulmool.lisboa.batch.scheduled

import me.ggulmool.lisboa.application.port.out.profits.SaveProfitsPort
import me.ggulmool.lisboa.application.port.out.stock.CollectStockPort
import me.ggulmool.lisboa.application.port.out.stock.LoadStockPort
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
    private val loadStockPort: LoadStockPort,
) : ApplicationRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(args: ApplicationArguments) {
        collectStockInfos()
        // 조회되는 년도를 localcache 적용해보기
    }

    fun collectStockInfos() {
        collectStockPort.getStockList().forEach {
            try {
                if (it.no == "035420") {
                val stock = parseStockPort.parse(it.no)
                if (stock.isActiveMarketTypes()) {
                    saveStockPort.saveSector(stock.sector)
                    saveStockPort.saveStock(stock)
                    saveProfitsPort.saveProfits(stock)

                    logger.info {"${it.no} =  ${loadStockPort.loadStock(it.no).calculateMarketCapitalization()}"}
                } else {
                    logger.info {"${it.name} + ${it.no}" }
                }
                }
            } catch (e: Exception) {
                logger.info {"${it.name} + ${it.no}" }
            }
        }
    }
}