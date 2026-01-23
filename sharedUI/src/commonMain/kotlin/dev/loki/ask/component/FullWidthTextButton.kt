package dev.loki.ask.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.loki.ask.theme.Constraint
import dev.loki.ask.theme.OnPoint

@Composable
fun FullWidthButton(
    text: String,
    onClick: () -> Unit,
    textColor: Color = Constraint,
    bgColor: Color = OnPoint,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(bgColor)
    ) {
        Text(
            text = text,
            color = textColor,
        )
    }
}