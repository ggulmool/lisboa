package me.ggulmool.lisboa.batch.scheduled

import me.ggulmool.lisboa.application.port.out.stock.CollectStockPort
import me.ggulmool.lisboa.application.port.out.stock.SaveStockPort
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(
    private val collectStockPort: CollectStockPort,
    private val saveStockPort: SaveStockPort,
) {
    private val logger = KotlinLogging.logger {}

//    @Scheduled(cron = "0 9 * * * MON") // 매주 월요일 오전 9시
//    fun collectBasicStockInfos() {
//        collectStockPort.getStockList().forEach {
//            try {
//                saveStockPort.saveStock(it)
//            } catch (e: Exception) {
//                logger.info { "${it.name} + ${it.no}" }
//            }
//        }
//    }
}