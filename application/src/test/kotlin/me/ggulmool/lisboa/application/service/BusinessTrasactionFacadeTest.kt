package me.ggulmool.lisboa.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.ggulmool.lisboa.application.service.test.BusinessInfoTransactionFacade
import me.ggulmool.lisboa.out.adapter.db.entity.common.findByIdOrThrow
import me.ggulmool.lisboa.out.adapter.db.entity.test.BusinessEntity
import me.ggulmool.lisboa.out.adapter.db.entity.test.BusinessId
import me.ggulmool.lisboa.out.adapter.db.entity.test.BusinessInfoRepository
import me.ggulmool.lisboa.web.WebApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource

//@Transactional // 하나의 테스트가 끝나고 rollback 처리
@SpringBootTest
@ContextConfiguration(
    classes = [
        WebApplication::class
    ]
)
@TestPropertySource(properties = ["spring.config.location=classpath:application-unittest.yml"])
class BusinessTrasactionFacadeTest(
    private val businessInfoTransactionFacade: BusinessInfoTransactionFacade,
    private val businessInfoRepository: BusinessInfoRepository,
) : BehaviorSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    Given("정상적인 케이스") {
        businessInfoRepository.save(BusinessEntity("1", "11", 2, "Y"))
        businessInfoRepository.save(BusinessEntity("1", "22", 1, "Y"))

        When("기존 1건이 저장된 상태에서 저장하면") {
            businessInfoTransactionFacade.saveBusiness("1", "33", "api1")

            Then("신규 저장되고 최근 저장한 순으로 seq 되어야 한다.") {
                val actual = businessInfoRepository.findByUidOrderBySeqAsc("1")
                actual.size shouldBe 3
                actual[0].bisno shouldBe "33"
                actual[0].seq shouldBe 1
                actual[1].bisno shouldBe "22"
                actual[1].seq shouldBe 2
                actual[2].bisno shouldBe "11"
                actual[2].seq shouldBe 3
            }
        }
    }

    Given("트랜잭션 테스트") {
        businessInfoRepository.save(BusinessEntity("1", "11", 1, "Y"))

        When("외부 api 호출이 실패하면 저장되지 않아야 한다.") {
            businessInfoTransactionFacade.saveBusiness("1", "33", "apiFailNo")
            Then("해당 uid로 조회시 기존 저장된 한건만 조회되어야 한다.") {
                val actual = businessInfoRepository.findByUidOrderByModifiedAtDesc("1")
                actual.size shouldBe 1

                shouldThrow<NoSuchElementException> {
                    businessInfoRepository.findByIdOrThrow(BusinessId("1", "33"))
                }
            }
        }
    }

    Given("소프트 delete 후 저장 테스트") {
        businessInfoRepository.save(BusinessEntity("1", "11", 1, "N"))

        When("기존 actYn이 N인건을 다시 등록하면") {
            businessInfoTransactionFacade.saveBusiness("1", "11", "api1")
            Then("actYn가 Y로 업데이트 되어야 한다.") {
                val actual = businessInfoRepository.findByIdOrThrow(BusinessId("1", "11"))
                actual.bisno shouldBe "11"
                actual.actYn shouldBe "Y"
            }
        }
    }

    afterEach {
        withContext(Dispatchers.IO) {
            businessInfoRepository.deleteAll()
        }
    }
})