package me.ggulmool.lisboa.out.adapter.db

import me.ggulmool.lisboa.application.port.out.test.SaveBusinessPort
import me.ggulmool.lisboa.domain.test.BusinessInfo
import me.ggulmool.lisboa.out.adapter.db.entity.test.BusinessEntity
import me.ggulmool.lisboa.out.adapter.db.entity.test.BusinessInfoRepository
import org.springframework.stereotype.Component

@Component
class BusinessDbAdapter(
    private val businessInfoRepository: BusinessInfoRepository
): SaveBusinessPort {

    override fun saveBusiness(businessInfo: BusinessInfo) {
        val businessEntity = BusinessEntity(
            businessInfo.uid,
            businessInfo.bisno,
            businessInfo.seq,
            businessInfo.actYn,
        )
        businessInfoRepository.save(businessEntity)
    }

    override fun updateBusinessSeqInfo(uid: String) {
        val businessInfos = businessInfoRepository.findByUidOrderByModifiedAtDesc(uid)
        businessInfos.forEachIndexed { index, businessEntity ->
            businessEntity.updateSeq(index + 1)
        }
    }
}