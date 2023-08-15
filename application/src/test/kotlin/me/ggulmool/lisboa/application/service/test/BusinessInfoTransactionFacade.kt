package me.ggulmool.lisboa.application.service.test

import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class BusinessInfoTransactionFacade(
    private val businessInfoSaveService: BusinessInfoSaveService,
) {
    private val logger = KotlinLogging.logger {}

    fun saveBusiness(uid: String, bisno: String, apiNo: String) {
        businessInfoSaveService.saveBusiness(uid, bisno, apiNo)
    }
}