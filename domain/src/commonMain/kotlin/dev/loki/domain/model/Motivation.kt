package dev.loki.domain.model

import dev.loki.domain.type.Category
import dev.loki.domain.type.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

sealed interface Motivation {

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
        val mileStones: List<dev.loki.domain.model.Milestone> = emptyList(),
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

    /**
     * 동기부여의 단위 중 기간이 정해진 목표
     * 사용자는 N개의 Milestone을 가질 수 있음
     */
    data class Milestone(
        val id: Int,
        val userId: Int,
        val goalId: Int,
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

    /**
     * 동기부여의 최소 단위
     * 사용자는 N개의 Achievement를 가질 수 있음
     */
    data class Achievement(
        val id: Int,
        val userId: Int,
        val milestoneId: Int,
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
}