package dev.loki.domain.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class Milestone(
    val id: Int,
    val title: String,
    val category: Category,
    val achievements: List<Achievement> = emptyList(),
    val priority: Priority,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isComplete: Boolean,
    val receiveNotification: Boolean,
)