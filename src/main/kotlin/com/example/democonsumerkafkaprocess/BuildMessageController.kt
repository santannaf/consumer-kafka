package com.example.democonsumerkafkaprocess

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.concurrent.thread

@RestController
@RequestMapping(path = ["/message"])
class BuildMessageController(
    private val publisherInterface: Publisher
) {
    //http://localhost:8081/subjects/pessoa-value/versions/1 url schema registry
    @PostMapping
    fun buildMessage() {
        publisherInterface.publisher(Pessoa(id = UUID.randomUUID().toString(), name = "Joao", age = 32), "pessoas")
//        publisherInterface.publisher(Pessoa(id = UUID.randomUUID().toString(), name = "Joao", age = 32, nickname = "nave mae", favorite = "Java", color = "white"), "pessoas")
    }
}