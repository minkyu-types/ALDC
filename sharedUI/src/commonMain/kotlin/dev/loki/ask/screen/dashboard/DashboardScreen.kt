package dev.loki.ask.screen.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.loki.ask.component.GradientCircularProgressIndicator
import dev.loki.ask.model.GoalModel
import dev.loki.ask.theme.AppTheme

@Composable
fun DashboardScreen(
    navigateTo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = DashboardUiState()

    val pageCount = uiState.goals.size
    val pagerState = rememberPagerState(initialPage = 0) { pageCount }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
        ) { page ->
            val goal = uiState.goals[page]
            DashboardPage(
                goal = goal,
                navigateTo = navigateTo
            )
        }
    }
}

@Composable
private fun DashboardPage(
    goal: GoalModel,
    navigateTo: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalProgress = goal.totalProgress

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navigateTo()
            }
    ) {
        Card {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                GradientCircularProgressIndicator(
                    progress = totalProgress / 100f,
                    trackBrush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFE0E0E0),
                            Color(0xFFE0E0E0)
                        )
                    ),
                    progressBrush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFFFFEB3B), // 노란색
                            Color(0xFFFFC107), // 주황빛 노란색
                            Color(0xFFFF9800), // 주황색
                            Color(0xFFFF5722), // 주황빛 빨간색
                            Color(0xFFF44336)  // 빨간색
                        )
                    ),
                    strokeWidth = 12.dp,
                    modifier = Modifier
                        .fillMaxSize()
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${totalProgress.toInt()}%",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "overall",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    AppTheme(onThemeChanged = {}) {
        DashboardScreen(
            navigateTo = {}
        )
    }
}