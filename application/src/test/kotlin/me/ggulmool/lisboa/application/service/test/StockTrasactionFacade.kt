package me.ggulmool.lisboa.application.service.test

import org.springframework.stereotype.Service

@Service
class StockTrasactionFacade(
    private val stockSaveService: StockSaveService,
) {

    fun saveStock(list: List<String>) {
        list.forEach {
            try {
                stockSaveService.saveStock(it)
            } catch (e: Exception) {
                println(e)
            }

        }
    }
}