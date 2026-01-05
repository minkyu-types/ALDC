package dev.loki.ask.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * @param progress 현재 진행률
 */
data class MilestoneModel(
    val id: Int,
    val title: String,
    val category: Category,
    val registeredDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val endDate: LocalDate,
    val progress: Float,
    val achievements: List<AchievementModel> = emptyList(),
    val priority: Priority,
    val isComplete: Boolean,
    val receiveNotification: Boolean,
)