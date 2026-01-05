package dev.loki.ask.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class AchievementModel(
    override val id: Int,
    override val title: String,
    override val category: Category,
    override val registeredDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    override val endDate: LocalDate,
    val priority: Priority,
    override val receiveNotification: Boolean,
) : HierarchicalModel<Nothing> {
    override val children: List<Nothing> = emptyList()
}
