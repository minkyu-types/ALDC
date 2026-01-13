package dev.loki.ask.screen.newMotivation

data class NewMotivationUiState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
