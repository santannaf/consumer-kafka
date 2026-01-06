package com.example.democonsumerkafkaprocess

import com.github.avrokotlin.avro4k.Avro
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ProducerMessage(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val avro: Avro
) : Publisher {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun publisher(message: Pessoa, topic: String) {

        log.info("publisher message to leader")

        val event = avro.toRecord(Pessoa.serializer(), message)

        kafkaTemplate.send(topic, event).handle { _, error ->
            if (error != null) log.error("error at publisher message to leader, error is: ${error.message}")
            else log.info("message was sent")
        }
    }
}