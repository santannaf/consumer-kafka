package com.example.democonsumerkafkaprocess

interface Publisher {
    fun publisher(message: Pessoa, topic: String)
}