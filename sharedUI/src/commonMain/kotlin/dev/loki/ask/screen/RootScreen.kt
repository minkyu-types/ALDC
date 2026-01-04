package dev.loki.ask.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import dev.loki.ask.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : NavKey

@Composable
fun RootScreen(
    modifier: Modifier = Modifier
) {
    val backStack = remember { mutableStateListOf<NavKey>(HomeRoute) }

    Scaffold(
        modifier = modifier.fillMaxSize()
            .systemBarsPadding()
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            entryProvider = entryProvider {
                entry<HomeRoute> {
                    HomeScreen(
                        onNavigate = {
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