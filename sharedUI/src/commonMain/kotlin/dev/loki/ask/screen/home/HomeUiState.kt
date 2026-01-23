package dev.loki.ask.screen.home

import dev.loki.ask.model.AchievementModel
import dev.loki.ask.model.GoalModel
import dev.loki.ask.model.MilestoneModel
import dev.loki.ask.model.QuoteModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class HomeUiState(
    val dayCount: Int = 1,
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val quote: QuoteModel = QuoteModel(),
    val goal: GoalModel? = null,
    val milestone: MilestoneModel? = null,
    val achievements: List<AchievementModel> = emptyList()
)
