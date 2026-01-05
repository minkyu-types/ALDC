package dev.loki.domain.model

import dev.loki.domain.type.Category
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class Goal(
    val id: Int,
    val title: String,
    val category: Category,
    val mileStones: List<Milestone> = emptyList(),
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val receiveNotification: Boolean,
)