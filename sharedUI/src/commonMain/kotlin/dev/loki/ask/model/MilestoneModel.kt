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
    override val id: Int,
    override val title: String,
    override val category: Category,
    override val registeredDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    override val endDate: LocalDate,
    val progress: Float,
    val achievements: List<AchievementModel> = emptyList(),
    val priority: Priority,
    val isComplete: Boolean,
    override val receiveNotification: Boolean,
) : HierarchicalModel<AchievementModel> {
    override val children: List<AchievementModel> get() = achievements
}