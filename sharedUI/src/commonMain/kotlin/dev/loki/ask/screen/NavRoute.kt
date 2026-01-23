package dev.loki.ask.screen

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface NavRoute: NavKey {

    @Serializable
    data object HomeScreen: NavRoute
}
