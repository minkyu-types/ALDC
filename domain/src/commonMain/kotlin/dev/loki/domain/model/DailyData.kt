package dev.loki.domain.model

import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Home 화면의 달력에 표시되는 데이터의 최소 단위
 */
@OptIn(ExperimentalUuidApi::class)
data class DailyData(
    val id: Uuid,
    val date: LocalDate,
    val quote: Quote,
    val currentProgress: Float,
    val totalProgress: Int,
    val motivations: List<Motivation>,
    val routines: List<Routine>,
)