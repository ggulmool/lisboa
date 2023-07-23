package me.ggulmool.lisboa.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication(scanBasePackages = ["me.ggulmool.lisboa"])
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}