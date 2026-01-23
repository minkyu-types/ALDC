package dev.loki.ask.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.loki.ask.component.TopBar
import dev.loki.ask.theme.AppTheme
import dev.loki.ask.theme.Constraint
import dev.loki.ask.theme.Primary
import dev.loki.ask.util.toFormatted

@Composable
fun HomeScreen(
    navigateTo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = HomeUiState()

    /**
     * TODO
     * 카드 뒤집기 효과를 넣자
     *
     * 리스트가 비어 있을 때에는 명언이 보이고,
     * 비어있지 않을 때에는 동기부여 항목들이 보인다.
     *
     * 비어있지 않을 때, 카드 뒤집기를 통해서 명언 페이지로 전환되도록
     */
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Primary)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            centerText = uiState.date.toFormatted("MM월 dd일")
        )
        Card {
            Text(
                text = uiState.quote.title,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                color = Constraint,
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme(onThemeChanged = {}) {
        HomeScreen(
            navigateTo = {}
        )
    }
}
