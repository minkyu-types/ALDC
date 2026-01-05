package dev.loki.domain.model

import kotlinx.datetime.LocalDate

/**
 * Home 화면의 달력에 표시되는 데이터의 최소 단위
 */
data class DailyData(
    val id: Int,
    val date: LocalDate,
    val quote: Quote,
    val currentProgress: Float,
    val totalProgress: Int,
)