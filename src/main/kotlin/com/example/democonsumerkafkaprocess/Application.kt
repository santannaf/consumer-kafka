package com.example.democonsumerkafkaprocess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
