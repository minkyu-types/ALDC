package dev.loki.domain.model

/**
 * Home 화면의 달력에 표시되는 데이터의 집합
 */
data class MonthlyData(
    val id: Int,
    val year: Int,
    val month: Int,
    val quote: String,
    val dailyData: List<DailyData>,
    val progress: Float,
)
