package dev.loki.domain.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * 동기부여의 단위 중 기간이 정해진 목표
 * 사용자는 N개의 Milestone을 가질 수 있음
 */
data class Milestone(
    val id: Int,
    val userId: Int,
    val goalId: Int?,
    val title: String,
    val category: Category,
    val achievements: List<Achievement> = emptyList(),
    val priority: Priority,
    val hasAchieved: Boolean,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val receiveNotification: Boolean,
): Motivation