package dev.loki.domain.model

import java.time.LocalDate

data class MonthlyData(
    val date: LocalDate,
    val quote: String,
    val dailyData: List<DailyData>,
    val progress: Float,
)
