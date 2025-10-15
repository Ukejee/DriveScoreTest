package com.ukejee.drivescoretest.creditScore.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CircularProgressComponent(
    progress: Float,
    maxProgress: Float,
    line1: String,
    line2: String,
    line3: String,
    modifier: Modifier = Modifier,
    circleSize: Dp = 300.dp,
    strokeWidth: Dp = 1.dp,
    progressStrokeWidth: Dp = 2.dp,
    margin: Dp = 6.dp,
    progressColor1: Color = Color.Green,
    progressColor3: Color = Color.Red,
    progressColor2: Color = progressColor1
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(circleSize)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepAngle = (progress / maxProgress) * 360f
            val outerRadius = (size.maxDimension / 2)
            val margin = margin.toPx()
            val arcDiameter = (outerRadius * 2) - (margin * 2)
            val strokeWidthPx = strokeWidth.toPx()
            val progressStrokeWidthPx = progressStrokeWidth.toPx()

            // Outer black circle
            drawArc(
                color = Color.Black,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(
                    (circleSize.toPx() - strokeWidthPx),
                    (circleSize.toPx() - strokeWidthPx)
                ),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )

            val gradientBrush = Brush.verticalGradient(
                colors = listOf(progressColor1, progressColor2, progressColor3)
            )

            // Progress arc
            drawArc(
                brush = gradientBrush,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(margin, margin),
                size = Size(arcDiameter, arcDiameter),
                style = Stroke(width = progressStrokeWidthPx, cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = line1,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = line2,
                fontSize = 28.sp,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                color = progressColor3
            )
            Text(
                text = line3,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CircularProgressComponentPreview() {
    CircularProgressComponent(
        progress = 200f,
        maxProgress = 500f,
        line1 = "Your credit score is",
        line2 = "200",
        line3 = "out of 500",
        progressColor1 = Color.Red,
        progressColor2 = Color.Yellow,
        progressColor3 = Color(0xFFFFA500)
    )
}


