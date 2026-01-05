package dev.loki.domain.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * 동기부여의 최종 목적
 * 사용자는 N개의 Goal을 가질 수 있음
 *
 * @param iWas 원래의 나
 * @param iWantToBe 원하는 나
 */
data class Goal(
    val id: Int,
    val title: String,
    val category: Category,
    val mileStones: List<Milestone> = emptyList(),
    val iWas: String,
    val iWantToBe: String,
    val priority: Priority,
    val hasAchieved: Boolean,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val receiveNotification: Boolean,
): Motivation