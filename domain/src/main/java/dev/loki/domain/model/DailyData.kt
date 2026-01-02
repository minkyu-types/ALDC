package dev.loki.domain.model

import java.time.LocalDate

data class DailyData(
    val date: LocalDate,
    val quote: String,
    val motivations: List<Motivation>,
    val progress: Float,
) {
    val isComplete: Boolean
        get() = motivations.all { it.isComplete }
}
