package me.ggulmool.lisboa.application.service.test

import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class BusinessInfoTransactionFacade(
    private val businessInfoSaveService: BusinessInfoSaveService,
) {
    private val logger = KotlinLogging.logger {}

    fun saveBusiness(uid: String, bisno: String, apiNo: String) {
        try {
            businessInfoSaveService.saveBusiness(uid, bisno, apiNo)
        } catch (e: Exception) {
            // API 실패 처리 및 기등록건 try { } catch
            logger.warn(e) { "저장 실패" }
        }
    }
}