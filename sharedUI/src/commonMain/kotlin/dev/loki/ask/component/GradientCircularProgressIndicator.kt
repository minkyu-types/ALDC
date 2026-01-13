package dev.loki.ask.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Gradient를 지원하는 Circular Progress Indicator
 *
 * @param progress 0.0 ~ 1.0 범위의 진행률
 * @param modifier Modifier
 * @param trackBrush track(배경)에 적용할 Brush (gradient 가능)
 * @param progressBrush progress에 적용할 Brush (gradient 가능)
 * @param strokeWidth 선의 두께
 */
@Composable
fun GradientCircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    trackBrush: Brush = Brush.linearGradient(
        colors = listOf(Color.LightGray, Color.Gray)
    ),
    progressBrush: Brush = Brush.sweepGradient(
        colors = listOf(Color.Blue, Color.Cyan, Color.Blue)
    ),
    strokeWidth: Dp = 8.dp,
) {
    Canvas(
        modifier = modifier
            .progressSemantics(progress)
            .size(48.dp)
    ) {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )
        
        // Track (배경) 그리기
        drawCircularIndicator(
            brush = trackBrush,
            startAngle = 0f,
            sweep = 360f,
            stroke = stroke
        )
        
        // Progress 그리기
        if (progress > 0f) {
            drawCircularIndicator(
                brush = progressBrush,
                startAngle = -90f, // 12시 방향부터 시작
                sweep = progress * 360f,
                stroke = stroke
            )
        }
    }
}

private fun DrawScope.drawCircularIndicator(
    brush: Brush,
    startAngle: Float,
    sweep: Float,
    stroke: Stroke
) {
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    
    drawArc(
        brush = brush,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}
