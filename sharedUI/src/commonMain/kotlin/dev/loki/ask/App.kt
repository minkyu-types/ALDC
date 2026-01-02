package dev.loki.ask

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import dev.loki.ask.navigation.MotivationHomeKey
import dev.loki.ask.navigation.NavState
import dev.loki.ask.theme.AppTheme

@Preview
@Composable
fun App(
    onThemeChanged: @Composable (isDark: Boolean) -> Unit = {},
) = AppTheme(onThemeChanged) {
    var backstack by remember {
        mutableStateOf(NavState(backstack = listOf(MotivationHomeKey)))
    }


}
