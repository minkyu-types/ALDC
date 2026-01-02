package dev.loki.ask.navigation

import kotlinx.serialization.Serializable

interface NavKey

@Serializable
data object MotivationHomeKey: NavKey

@Serializable
data class MotivationDetailKey(val id: String): NavKey