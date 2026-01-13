package dev.loki.ask.screen.newMotivation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewMotivationScreen(
    viewModel: NewMotivationViewModel,
    navigateBack: () -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.value

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is NewMotivationEffect.NavigateBack -> navigateBack()
                is NewMotivationEffect.ShowToast -> onShowToast(effect.message)
            }
        }
    }

    NewMotivationContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
private fun NewMotivationContent(
    uiState: NewMotivationUiState,
    onEvent: (NewMotivationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // TODO: UI 컴포넌트 구현
        // 예시:
        // - TextField for description
        // - Save Button
        // - Back Button
        // - Loading indicator when isLoading is true
        // - Error message display when errorMessage is not null
    }
}