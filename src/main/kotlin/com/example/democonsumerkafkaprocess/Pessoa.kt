package com.example.democonsumerkafkaprocess

import kotlinx.serialization.Serializable

@Serializable
data class Pessoa (
    val id: String,
    val name: String,
    val age: Int? = null,
//    val nickname: String? = null,
//    val favorite: String? = null,
//    val color: String? = null
)