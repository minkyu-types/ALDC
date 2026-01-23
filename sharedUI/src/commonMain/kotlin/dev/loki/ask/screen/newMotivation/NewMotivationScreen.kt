package dev.loki.ask.screen.newMotivation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.loki.ask.component.CircleBgIcon
import dev.loki.ask.theme.AppTheme
import dev.loki.domain.type.Category
import dev.loki.domain.type.RepeatType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalDateTime

// 동기부여 항목 추가 or 수정 하는 화면
@Composable
fun NewMotivationScreen(
    viewModel: NewMotivationViewModel,
    navigateBack: () -> Unit,
    onShowToast: (String) -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit,
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
        MotivationTitle(
            title = "",
            onTitleChange = { title ->

            }
        )
        MotivationIcons(
            icons = listOf("1", "2"),
            onIconChange = { iconName ->

            }
        )
        MotivationCategories(
            selectedCategory = Category.HEALTH_FITNESS,
            categories = listOf(Category.HEALTH_FITNESS, Category.PERSONAL_GROWTH),
            onCategoryChange = { category ->

            },
        )
    }
}

// 제목
@Composable
private fun MotivationTitle(
    title: String = "",
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "제목"
        )
        TextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxSize()
        )
    }
}

// 아이콘
// TODO 카테고리 객체 추가
@Composable
private fun MotivationIcons(
    selectedIcon: String? = null,
    icons: List<String>,
    onIconChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(
            count = icons.size,
            key = { icons[it] }
        ) { index ->
            val item = icons[index]

            CircleBgIcon(
                icon = Icons.Filled.Add,
                isSelected = (selectedIcon == item),
                onClick = {
                    onIconChange(item)
                }
            )
        }
    }
}

// 카테고리
@Composable
private fun MotivationCategories(
    selectedCategory: Category,
    categories: List<Category>,
    onCategoryChange: (Category) -> Unit,
    modifier: Modifier = Modifier
) {

}

// 목표 달성 일자
@Composable
private fun MotivationDeadline(
    deadline: LocalDateTime,
    deadlineType: RepeatType,
    onClick: (LocalDateTime) -> Unit,
    onDeadlineChange: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(deadline)
            }
    ) {
        Text(
            text = deadline.year.toString()
        )
        VerticalDivider(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 4.dp)
        )
        Text(
            text = deadline.month.toString()
        )
        Text(
            text = "${deadline.date}(${deadline.dayOfWeek.name})"
        )
    }
}

/**
 * 알림 설정 ( 유 료 화 ? )
 */
@Composable
private fun MotivationNotification(
    receiveNotification: Boolean,
    onNotificationChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.Alarm,
            contentDescription = null
        )
        Column(

        ) {
            Text(
                text = "알림"
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "정해진 시간에 알림을 보냅니다",
                modifier = Modifier
                    .weight(1f)
            )
        }
        Switch(
            checked = true,
            onCheckedChange = { checked ->

            }
        )
    }
}

@Preview
@Composable
private fun NewMotivationContentPreview() {
    AppTheme(onThemeChanged = {}) {
        NewMotivationContent(
            uiState = NewMotivationUiState(),
            onEvent = {}
        )
    }
}