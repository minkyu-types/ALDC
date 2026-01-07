package dev.loki.domain.model

import kotlinx.datetime.LocalDate

data class Weakness(
    val id: Int,
    val userId: Int,
    val title: String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
)
