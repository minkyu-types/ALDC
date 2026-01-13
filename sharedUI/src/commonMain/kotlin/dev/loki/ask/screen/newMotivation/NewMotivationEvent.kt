package dev.loki.ask.screen.newMotivation

sealed interface NewMotivationEvent {
    data class OnTitleChange(val title: String) : NewMotivationEvent
    data class OnDescriptionChange(val description: String) : NewMotivationEvent
    data object OnSaveClick : NewMotivationEvent
    data object OnBackClick : NewMotivationEvent
}
