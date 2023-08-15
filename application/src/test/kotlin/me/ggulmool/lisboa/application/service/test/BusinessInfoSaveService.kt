package me.ggulmool.lisboa.application.service.test

import me.ggulmool.lisboa.application.port.out.test.SaveBusinessPort
import me.ggulmool.lisboa.domain.test.BusinessInfo
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BusinessInfoSaveService(
    private val saveBusinessPort: SaveBusinessPort,
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    fun saveBusiness(uid: String, bisno: String, apiNo: String) {
        saveBusinessPort.saveBusiness(
            BusinessInfo(uid, bisno, 1, "Y")
        )
        saveBusinessPort.updateBusinessSeqInfo(uid)
        try {
            apiCall(apiNo)
        } catch (e: IllegalArgumentException) {
            logger.warn(e) { "이미 기가입된 회원입니다." }
        } catch (e: Exception) {
            throw e
        }
    }

    fun apiCall(apiNo: String) {
        if (apiNo == "apiFailNo") {
            throw IllegalStateException("api call error")
        } else if (apiNo == "dup") {
            throw IllegalArgumentException("duplication")
        }
    }
}