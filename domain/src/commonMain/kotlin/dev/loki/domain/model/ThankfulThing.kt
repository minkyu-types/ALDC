package dev.loki.domain.model

import kotlinx.datetime.LocalDateTime

/**
 * 감사한 일
 * CaveDiary에 리스트 형태로 들어간다
 */
data class ThankfulThing(
    val id: Int,
    val caveDiaryId: Int,
    val title: String,
    val createdAt: LocalDateTime
)
