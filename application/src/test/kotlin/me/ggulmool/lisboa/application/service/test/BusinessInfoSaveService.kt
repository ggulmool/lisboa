package me.ggulmool.lisboa.application.service.test

import me.ggulmool.lisboa.application.port.out.test.SaveBusinessPort
import me.ggulmool.lisboa.domain.test.BusinessInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BusinessInfoSaveService(
    private val saveBusinessPort: SaveBusinessPort,
) {

    @Transactional
    fun saveBusiness(uid: String, bisno: String, apiNo: String) {
        saveBusinessPort.saveBusiness(
            BusinessInfo(uid, bisno, 1, "Y")
        )
        saveBusinessPort.updateBusinessSeqInfo(uid)
        apiCall(apiNo)
    }

    fun apiCall(apiNo: String) {
        if (apiNo == "apiFailNo") {
            throw IllegalStateException("api call error")
        }
    }
}