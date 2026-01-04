package dev.loki.domain.model

import java.time.LocalDate

data class DailyData(
    val date: LocalDate,
    val quote: String,
    val motivations: List<Motivation>,
    val currentProgress: Float,
    val totalProgress: Int,
) {
    val isComplete: Boolean
        get() = motivations.all { it.isComplete }
}
