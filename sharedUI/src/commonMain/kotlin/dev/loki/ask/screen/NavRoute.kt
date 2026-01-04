package dev.loki.ask.screen

import dev.loki.ask.navigation.NavKey
import kotlinx.serialization.Serializable

interface NavRoute: NavKey {

    @Serializable
    data object HomeScreen: NavRoute
}