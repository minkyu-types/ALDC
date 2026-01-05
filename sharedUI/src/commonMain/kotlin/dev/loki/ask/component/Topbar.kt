package dev.loki.ask.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import ask_your_motivation_amplifier.sharedui.generated.resources.Res
import ask_your_motivation_amplifier.sharedui.generated.resources.img_ask_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBar(
    centerText: String,
    backgroundColor: Color = Color.Transparent,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Icon(
            painter = painterResource(Res.drawable.img_ask_logo),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .align(Alignment.CenterStart)
        )
        Text(
            text = centerText,
            modifier = Modifier
                .align(Alignment.Center)
        )
        ReadableIcon(
            hasRead = true,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {

                }
        ) {
            Icon(
                painter = painterResource(Res.drawable.img_ask_logo),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}
