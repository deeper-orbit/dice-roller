package com.deeperorbit.diceroller.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Main pill action button for triggering a dice roll with theme adaptability.
 */
@Composable
fun RollButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = MaterialTheme.colorScheme.primaryContainer
    val contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    val badgeBg = if (contentColor == Color(0xFFEDEDED) || containerColor == Color(0xFF383838)) {
        Color.White
    } else {
        Color(0xFF191919)
    }
    val badgeIconColor = if (badgeBg == Color.White) Color(0xFF191919) else Color.White

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        ),
        modifier = modifier
            .testTag("roll_dice_button")
            .height(58.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DiceActionBadge(
                circleColor = badgeBg,
                diceColor = badgeIconColor,
                size = 28.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Roll a Dice",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = contentColor
            )
        }
    }
}

/**
 * Vector Badge displaying a rounded die face with 5 pips.
 */
@Composable
fun DiceActionBadge(
    circleColor: Color,
    diceColor: Color,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .background(circleColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size * 0.58f)) {
            val w = this.size.width
            val h = this.size.height
            val corner = w * 0.22f

            // Draw rounded die face outline
            drawRoundRect(
                color = diceColor,
                topLeft = Offset(0f, 0f),
                size = Size(w, h),
                cornerRadius = CornerRadius(corner, corner),
                style = Stroke(width = w * 0.12f)
            )

            // Draw 5 standard dice pips (dots)
            val dotRadius = w * 0.08f
            // Center dot
            drawCircle(color = diceColor, radius = dotRadius, center = Offset(w * 0.5f, h * 0.5f))
            // 4 Corner dots
            drawCircle(color = diceColor, radius = dotRadius, center = Offset(w * 0.28f, h * 0.28f))
            drawCircle(color = diceColor, radius = dotRadius, center = Offset(w * 0.72f, h * 0.28f))
            drawCircle(color = diceColor, radius = dotRadius, center = Offset(w * 0.28f, h * 0.72f))
            drawCircle(color = diceColor, radius = dotRadius, center = Offset(w * 0.72f, h * 0.72f))
        }
    }
}
