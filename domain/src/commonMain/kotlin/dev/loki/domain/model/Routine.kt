package dev.loki.domain.model

import kotlinx.datetime.LocalDateTime

data class Routine(
    val id: Int,
    val title: String,
    val createdAt: LocalDateTime,
)