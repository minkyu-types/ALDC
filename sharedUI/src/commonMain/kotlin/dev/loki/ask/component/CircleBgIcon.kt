package dev.loki.ask.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import dev.loki.ask.theme.OnPoint
import dev.loki.ask.theme.Primary

@Composable
fun CircleBgIcon(
    icon: ImageVector,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(32.dp)
            .background(Primary, CircleShape)
            .clip(CircleShape)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) OnPoint else Color.Transparent
            )
    ) {
        Icon(
            painter = rememberVectorPainter(icon),
            contentDescription = null
        )
    }
}