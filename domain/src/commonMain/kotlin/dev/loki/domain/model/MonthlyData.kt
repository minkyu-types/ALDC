package dev.loki.domain.model

data class MonthlyData(
    val id: Int,
    val year: Int,
    val month: Int,
    val quote: String,
    val dailyData: List<DailyData>,
    val progress: Float,
)
