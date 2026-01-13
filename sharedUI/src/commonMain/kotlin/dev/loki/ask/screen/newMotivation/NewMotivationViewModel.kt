package dev.loki.ask.screen.newMotivation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NewMotivationViewModel : ViewModel() {
    private val _uiState = mutableStateOf(NewMotivationUiState())
    val uiState: State<NewMotivationUiState> = _uiState

    private val _effect = Channel<NewMotivationEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: NewMotivationEvent) {
        when (event) {
            is NewMotivationEvent.OnTitleChange -> {
                _uiState.value = _uiState.value.copy(title = event.title)
            }
            is NewMotivationEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }
            is NewMotivationEvent.OnSaveClick -> {
                handleSave()
            }
            is NewMotivationEvent.OnBackClick -> {
                sendEffect(NewMotivationEffect.NavigateBack)
            }
        }
    }

    private fun handleSave() {
        viewModelScope.launch {
            val currentState = _uiState.value
            
            if (currentState.title.isBlank()) {
                sendEffect(NewMotivationEffect.ShowToast("제목을 입력해주세요"))
                return@launch
            }
            
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // TODO: 실제 저장 로직 구현
                // 예: repository.saveMotivation(currentState.title, currentState.description)
                
                sendEffect(NewMotivationEffect.ShowToast("저장되었습니다"))
                sendEffect(NewMotivationEffect.NavigateBack)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "저장 중 오류가 발생했습니다"
                )
                sendEffect(NewMotivationEffect.ShowToast("저장에 실패했습니다"))
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun sendEffect(effect: NewMotivationEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}