package com.example.democonsumerkafkaprocess

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

//@Component
//class ConsumerMessageError {
//    private val log = LoggerFactory.getLogger(javaClass)
//
//    @KafkaListener(groupId = "group-hello", topics = ["cmd.hello.error"])
//    fun onMessage(event: ConsumerRecord<String, String>, ack: Acknowledgment) {
//        when (val message = event.value()) {
//            null -> log.warn("message is empty or null")
//            else -> {}//log.info("offset=${event.offset()}, topic=${event.topic()}, message=$message")
//        }
//    }
//}
