package dev.loki.domain.model

import kotlinx.datetime.LocalDateTime

/**
 * 동굴 일기
 * 매일 작성하는 다이어리(회고)
 */
data class CaveDiary(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val motivations: List<Motivation>,
    val thankfulThings: List<ThankfulThing>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)