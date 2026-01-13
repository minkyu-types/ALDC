package dev.loki.ask.screen.newMotivation

sealed interface NewMotivationEffect {
    data object NavigateBack : NewMotivationEffect
    data class ShowToast(val message: String) : NewMotivationEffect
}
