package me.ggulmool.lisboa.application.service

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.mockk.every
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.ggulmool.lisboa.application.port.out.stock.ParseStockPort
import me.ggulmool.lisboa.application.service.test.StockTrasactionFacade
import me.ggulmool.lisboa.domain.common.Money
import me.ggulmool.lisboa.domain.profits.QuarterProfits
import me.ggulmool.lisboa.domain.profits.YearProfits
import me.ggulmool.lisboa.domain.stock.MarketType
import me.ggulmool.lisboa.domain.stock.Sector
import me.ggulmool.lisboa.domain.stock.Stock
import me.ggulmool.lisboa.domain.stock.StockQuantity
import me.ggulmool.lisboa.out.adapter.db.entity.stock.StockRepository
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
class StockTrasactionFacadeTest(
    @MockkBean private val parseStockPort: ParseStockPort,
    private val stockTrasactionFacade: StockTrasactionFacade,
    private val stockRepository: StockRepository,
) : BehaviorSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    Given("정상적인 케이스") {
        every { parseStockPort.parse(any()) } returns Stock(
            stockNo = "111111",
            marketType = MarketType.KOSPI,
            name = "name11111",
            currentPrice = Money("1000"),
            stockQuantity = StockQuantity("10"),
            sector = Sector.ADVERTISING,
            yearProfits = YearProfits(),
            quarterProfits = QuarterProfits(),
            description = "description"
        )
        When("2건을 저장하면") {
            stockTrasactionFacade.saveStock(listOf("111111", "222222"))
            val actual = stockRepository.findAll()
            Then("2건이 조회된다.") {
                actual.size shouldBe 2
            }
        }
    }

    Given("트랜잭션 테스트") {
        every { parseStockPort.parse("111111") } returns Stock(
            stockNo = "111111",
            marketType = MarketType.KOSPI,
            name = "name11111",
            currentPrice = Money("1000"),
            stockQuantity = StockQuantity("10"),
            sector = Sector.ADVERTISING,
            yearProfits = YearProfits(),
            quarterProfits = QuarterProfits(),
            description = "description"
        )
        every { parseStockPort.parse("222222") } throws IllegalStateException("실패")
        When("2건중 1건이 실패하면") {
            stockTrasactionFacade.saveStock(listOf("111111", "222222"))
            val actual = stockRepository.findAll()
            Then("1건만 저장되어야 한다.") {
                actual.size shouldBe 1
            }
        }
    }

    afterEach {
        withContext(Dispatchers.IO) {
            stockRepository.deleteAll()
        }
    }
})