package com.deeperorbit.diceroller.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Action button for the "Maybe" / "Try your luck" tab.
 */
@Composable
fun MaybeButton(
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
            .testTag("maybe_flip_button")
            .height(58.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MaybeActionBadge(
                circleColor = badgeBg,
                iconColor = badgeIconColor,
                size = 28.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Try your luck",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = contentColor
            )
        }
    }
}

@Composable
fun MaybeActionBadge(
    circleColor: Color,
    iconColor: Color,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .background(circleColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(size * 0.58f)
        )
    }
}
