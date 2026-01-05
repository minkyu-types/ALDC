package dev.loki.domain.model

data class DailyRoutine(
    val id: Int,
    val title: String,
    val routines: List<Routine>,
)