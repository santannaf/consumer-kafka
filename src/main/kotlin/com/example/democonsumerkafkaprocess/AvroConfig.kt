package com.example.democonsumerkafkaprocess

import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.serializer.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AvroConfig {
    @Bean
    fun avro(): Avro {
        return Avro(
            SerializersModule {
                contextual(UUIDSerializer())
                contextual(BigDecimalSerializer())
                contextual(BigIntegerSerializer())
                contextual(LocalDateSerializer())
                contextual(LocalDateTimeSerializer())
                contextual(LocalTimeSerializer())
            }
        )
    }
}