package dev.loki.domain.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * 동기부여의 최소 단위
 * 사용자는 N개의 Achievement를 가질 수 있음
 */
data class Achievement(
    val id: Int,
    val title: String,
    val category: Category,
    val priority: Priority,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val hasAchieved: Boolean,
    val receiveNotification: Boolean,
): Motivation