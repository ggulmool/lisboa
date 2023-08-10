package me.ggulmool.lisboa.application.port.out.test

import me.ggulmool.lisboa.domain.test.BusinessInfo

interface SaveBusinessPort {

    fun saveBusiness(businessInfo: BusinessInfo)

    fun updateBusinessSeqInfo(uid: String)
}