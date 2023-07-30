package me.ggulmool.lisboa.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["me.ggulmool.lisboa"])
class WebApplication

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}