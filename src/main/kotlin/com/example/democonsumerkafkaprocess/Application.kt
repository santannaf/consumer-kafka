package com.example.democonsumerkafkaprocess

import com.tanna.annotation.EnabledArchKafka
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.core.publisher.Hooks

@SpringBootApplication(scanBasePackages = ["com"])
@EnabledArchKafka(appName = "consumer-xyz")
class Application

fun main(args: Array<String>) {
    Hooks.enableAutomaticContextPropagation()
    runApplication<Application>(*args)
}
