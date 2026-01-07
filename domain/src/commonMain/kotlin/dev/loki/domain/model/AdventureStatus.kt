package dev.loki.domain.model

data class AdventureStatus(
    val id: Int,
    val userId: Int,
    val achieveInARow: Int,
    val completedGoals: Int
)
