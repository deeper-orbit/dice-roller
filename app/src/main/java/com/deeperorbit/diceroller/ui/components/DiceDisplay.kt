package com.deeperorbit.diceroller.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeperorbit.diceroller.domain.NumberSystem
import com.deeperorbit.diceroller.graphics.drawDiceShape
import com.deeperorbit.diceroller.graphics.drawMorphDiceShape

/**
 * Centered dynamic dice display with Material 3 shape morphing canvas
 * and optical numeral alignment adapting to current theme.
 */
@Composable
fun DiceDisplay(
    activeNumber: Int,
    startShapeNumber: Int,
    targetShapeNumber: Int,
    morphProgress: Float,
    scale: Float,
    isRolling: Boolean,
    numberSystem: NumberSystem,
    onDiceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedText = numberSystem.format(activeNumber)
    val diceColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = modifier
            .size(220.dp)
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onDiceClick()
            }
            .testTag("dice_display"),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (isRolling) {
                drawMorphDiceShape(
                    startNumber = startShapeNumber,
                    endNumber = targetShapeNumber,
                    progress = morphProgress,
                    color = diceColor
                )
            } else {
                drawDiceShape(
                    number = activeNumber,
                    color = diceColor
                )
            }
        }

        // Display the number formatted according to the selected number system
        val fontSize = when {
            formattedText.length >= 3 -> 54.sp
            formattedText.length == 2 -> 68.sp
            else -> 82.sp
        }

        Text(
            text = formattedText,
            color = textColor,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .offsetNumberForGeometry(activeNumber)
        )
    }
}

/**
 * Optical centering adjustment for numbers inside geometric shapes.
 */
private fun Modifier.offsetNumberForGeometry(number: Int): Modifier {
    return when (number) {
        3 -> this.graphicsLayer { translationY = 8f }
        2 -> this.graphicsLayer { translationY = -2f }
        5 -> this.graphicsLayer { translationY = 4f }
        else -> this
    }
}
