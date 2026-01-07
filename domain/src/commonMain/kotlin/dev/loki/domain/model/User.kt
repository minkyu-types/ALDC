package dev.loki.domain.model

import dev.loki.domain.type.Gender
import kotlinx.datetime.LocalDateTime

/**
 * 일반 유저 정보 (앱 내부 사용)
 */
data class User(
    val userId: Int,
    val anonymousId: Int?,
    val realName: String,
    val email: String,
    val phone: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

/**
 * 공개 프로필 정보 (커뮤니티에 노출)
 */
data class AnonymousUser(
    val anonymousId: String,
    val nickname: String,
    val age: Int,
    val gender: Gender,
    val createdAt: LocalDateTime,
    val profileImageUrl: String? = null,
)

/**
 * 실제 사용자 정보 (서버/DB에만 저장, 노출 금지)
 */
data class PrivateUserData(
    val userId: Int,
    val anonymousId: String,
    val realName: String,
    val email: String,
    val phone: String? = null,
    val gender: Gender,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)