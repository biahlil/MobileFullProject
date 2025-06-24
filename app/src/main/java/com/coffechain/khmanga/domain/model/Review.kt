package com.coffechain.khmanga.domain.model

import java.util.Date

data class Review(
    val id: String,
    val userId: String,
    val userName: String,
    val rating: Double,
    val comment: String,
    val createdAt: Date
)