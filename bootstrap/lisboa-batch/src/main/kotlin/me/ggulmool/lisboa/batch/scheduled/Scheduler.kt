package me.ggulmool.lisboa.batch.scheduled

import me.ggulmool.lisboa.application.port.out.stock.CollectStockPort
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(
    private val collectStockPort: CollectStockPort,
    private val saveStockPort: SaveStockPort,
    private val trasactionFacade: TrasactionFacade,
): ApplicationRunner {
    private val logger = KotlinLogging.logger {}

    @Scheduled(cron = "0 9 * * * MON") // 매주 월요일 오전 9시
    fun collectStockList() {
        collectStockPort.getStockList().forEach {
            try {
                saveStockPort.saveStock(it)
            } catch (e: Exception) {
                logger.info { "${it.name} + ${it.no}" }
            }
        }
    }

    override fun run(args: ApplicationArguments) {
        for (i in 1..4) {
            try {
                trasactionFacade.trans(i)
            } catch (e: Exception) {
                logger.info { "failed catched" }
            }
        }
    }
}