package dev.loki.ask.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class AchievementModel(
    val id: Int,
    val title: String,
    val category: Category,
    val registeredDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val endDate: LocalDate,
    val priority: Priority,
    val receiveNotification: Boolean,
)
