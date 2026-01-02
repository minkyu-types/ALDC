package dev.loki.domain.model

import dev.loki.domain.type.MotivationType

data class Motivation(
    val id: Int,
    val type: MotivationType,
    val title: Int,
    val description: Int,
    val isComplete: Boolean
)