package dev.loki.ask

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.loki.ask.screen.home.HomeScreen
import dev.loki.ask.theme.AppTheme
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

@Composable
fun App(
    onThemeChanged: @Composable (isDark: Boolean) -> Unit = {},
) = AppTheme(onThemeChanged) {
    val backStack = remember { mutableStateListOf<NavKey>(HomeRoute) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            entryProvider = entryProvider {
                entry<HomeRoute> {
                    HomeScreen(
                        navigateTo = {
                            // TODO: 다른 화면으로 네비게이션
                        }
                    )
                }
            },
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.lastIndex)
                }
            }
        )
    }
}

@Preview
@Composable
private fun AppPreview() {
    App()
}
