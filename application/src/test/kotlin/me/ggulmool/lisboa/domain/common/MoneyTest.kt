package me.ggulmool.lisboa.domain.common

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.ggulmool.lisboa.domain.common.Money
import java.math.BigDecimal

class MoneyTest : StringSpec({

    "가격 문자열콤마 제거 테스트" {
        val money = Money("100,100,100")
        money shouldBe Money("100100100")
        money.price shouldBe BigDecimal("100100100")
    }

    "원화 표시 테스트" {
        Money("10000").moneyFormat() shouldBe "10,000"
        Money("123456789").moneyFormat() shouldBe "123,456,789"
    }

    "원화 억원 단위 표시 테스트" {
        Money("100,000,000,000").billionFormat() shouldBe "1,000"
        Money("2,313,510,000,000").billionFormat() shouldBe "23,135"
    }
})
