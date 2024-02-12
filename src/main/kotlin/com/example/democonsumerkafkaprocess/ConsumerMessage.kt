package com.example.democonsumerkafkaprocess

import com.github.avrokotlin.avro4k.Avro
import kotlinx.coroutines.*
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class ConsumerMessage(
    private val producer: Publisher,
    private val avro: Avro
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private suspend fun <T, R> List<T>.mapAsync(
        mapper: suspend (T) -> R
    ): List<R> = coroutineScope { map { async(Dispatchers.Default) { mapper(it) } }.awaitAll() }

    private fun processMessage(event: ConsumerRecord<String, GenericRecord>) {
        when (val message = event.value()) {
            null -> log.warn("message is empty or null")
            else -> {
                try {
                    //gravar no banco aquele offset // topic name + offset + partition > message
//                    simulateExternalCall(message, event.offset())
                    val pessoa = avro.fromRecord(Pessoa.serializer(), message)
                    log.info("chegou uma pessoa: $pessoa")
                } catch (error: Exception) {
//                    producer.publisher(message, "cmd.hello.error")
                }
            }
        }
    }

    @KafkaListener(groupId = "group-bank", topics = ["pessoas"])
    fun onMessage(batch: MutableList<ConsumerRecord<String, GenericRecord>>, ack: Acknowledgment) {
        log.info("received data on size {}", batch.count())

        runBlocking {
            batch.mapAsync { event -> processMessage(event) }
        }

        ack.acknowledge() //comitar todo o poll 1500 records ou o numero que tiver no poll
        log.info("batch was finish on size of {}", batch.size)
    }

    private fun simulateExternalCall(message: String, offset: Long) {
//        val time = Random.nextLong(1, 20)
//        Thread.sleep(time)
        if (Random.nextLong(85, 900) % 2 == 0L) {
            throw Exception("An exception was throw, just test, message: $message, offset:$offset")
        }
    }
}
