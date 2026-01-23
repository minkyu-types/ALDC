package dev.loki.ask.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.loki.ask.theme.AppTheme
import dev.loki.ask.theme.OnError
import dev.loki.ask.theme.Primary

/**
 * @author mkkim
 * 읽음 여부에 따라 우측 상단의 빨간 도트가 보여지는 아이콘
 */
@Composable
fun ReadableIcon(
    hasRead: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        content()
        if (!hasRead) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(OnError, CircleShape)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Preview
@Composable
private fun ReadableIconPreview() {
    AppTheme(onThemeChanged = {}) {
        ReadableIcon(
            hasRead = false
        ) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(24.dp)
                    .background(Primary, CircleShape)
            )
        }
    }
}