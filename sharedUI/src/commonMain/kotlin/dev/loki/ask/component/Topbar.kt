package dev.loki.ask.component

import a_long_dark_cave.sharedui.generated.resources.Res
import a_long_dark_cave.sharedui.generated.resources.img_aldc_logo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBar(
    centerText: String,
    backgroundColor: Color = Color.Transparent,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(52.dp)
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.img_aldc_logo),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = centerText,
        )
        ReadableIcon(
            hasRead = true,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {

                }
        ) {
            Icon(
                painter = painterResource(Res.drawable.img_aldc_logo),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}
