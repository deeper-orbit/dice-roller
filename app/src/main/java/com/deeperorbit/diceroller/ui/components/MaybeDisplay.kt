package com.deeperorbit.diceroller.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeperorbit.diceroller.domain.MaybeOutcome

/**
 * Interactive 3D flipping card displaying Thumb Up (Yes) and Thumb Down (No) decisions.
 */
@Composable
fun MaybeDisplay(
    activeOutcome: MaybeOutcome,
    isFlipping: Boolean,
    rotation: Float,
    scale: Float,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background == Color(0xFF000000)

    val cardBg = if (isDark) {
        if (!isFlipping) {
            if (activeOutcome == MaybeOutcome.YES) Color(0xFF132F1A) else Color(0xFF331518)
        } else {
            Color(0xFF222222)
        }
    } else {
        if (!isFlipping) {
            if (activeOutcome == MaybeOutcome.YES) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
        } else {
            Color(0xFFFFFFFF)
        }
    }

    val contentColor = if (activeOutcome == MaybeOutcome.YES) {
        Color(0xFF2E7D32)
    } else {
        Color(0xFFC62828)
    }

    val animatedCardBg by animateColorAsState(targetValue = cardBg, label = "card_bg")
    val animatedContentColor by animateColorAsState(targetValue = contentColor, label = "card_fg")

    val normalizedAngle = (rotation % 360f + 360f) % 360f
    val isBackFace = normalizedAngle in 90f..270f

    val effectiveOutcome = if (isFlipping) {
        if (isBackFace) activeOutcome.opposite() else activeOutcome
    } else {
        activeOutcome
    }

    val icon = if (effectiveOutcome == MaybeOutcome.YES) Icons.Default.ThumbUp else Icons.Default.ThumbDown
    val label = if (effectiveOutcome == MaybeOutcome.YES) "YES" else "NO"

    Box(
        modifier = modifier
            .size(220.dp)
            .scale(scale)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 16f * density
            }
            .shadow(
                elevation = if (isDark) 0.dp else 8.dp,
                shape = CircleShape,
                spotColor = animatedContentColor.copy(alpha = 0.25f)
            )
            .clip(CircleShape)
            .background(animatedCardBg)
            .border(
                width = 3.dp,
                color = animatedContentColor.copy(alpha = if (isFlipping) 0.3f else 0.85f),
                shape = CircleShape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCardClick()
            }
            .testTag("maybe_display"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.graphicsLayer {
                // Prevent mirrored text when backface is visible
                if (isBackFace) {
                    rotationY = 180f
                }
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = animatedContentColor,
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = label,
                color = animatedContentColor,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 2.sp
            )
        }
    }
}
