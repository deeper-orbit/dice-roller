package com.deeperorbit.diceroller.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeperorbit.diceroller.ui.components.DiceDisplay
import com.deeperorbit.diceroller.ui.components.NumberSystemSelector
import com.deeperorbit.diceroller.ui.components.RollButton

/**
 * Main Dice Roller Screen composable with 1s background inversion effect on rolling 6.
 */
@Composable
fun DiceRollerScreen(
    modifier: Modifier = Modifier,
    state: DiceRollerState = rememberDiceRollerState(),
    onNavigateToSettings: () -> Unit = {}
) {
    val defaultBg = MaterialTheme.colorScheme.background
    val isDark = defaultBg == Color(0xFF000000)

    // Calculate background color with 1s inversion on rolling a 6
    val targetBgColor = if (state.isSixInverted) {
        if (isDark) Color(0xFFF2F2F0) else Color(0xFF000000)
    } else {
        defaultBg
    }

    val animatedBgColor by animateColorAsState(
        targetValue = targetBgColor,
        animationSpec = tween(durationMillis = 180),
        label = "dice_screen_bg"
    )

    val placeholderColor = if (isDark) Color(0xFF6E6E6E) else Color(0xFFB0B0AE)

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(animatedBgColor),
        color = animatedBgColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Main Center Dice Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (state.currentNumber == null && !state.isRolling) {
                    Text(
                        text = "Try rolling dice",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = placeholderColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .testTag("placeholder_text")
                            .padding(bottom = 64.dp)
                    )
                } else {
                    val activeNumber = if (state.isRolling) state.displayedNumber else (state.currentNumber ?: 1)
                    DiceDisplay(
                        activeNumber = activeNumber,
                        startShapeNumber = state.startShapeNumber,
                        targetShapeNumber = state.targetShapeNumber,
                        morphProgress = state.morphProgress.value,
                        scale = state.scaleAnim.value,
                        isRolling = state.isRolling,
                        numberSystem = state.selectedNumberSystem,
                        onDiceClick = { state.roll() }
                    )
                }
            }

            // Bottom Controls Area
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 36.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NumberSystemSelector(
                    selectedSystem = state.selectedNumberSystem,
                    onSelectSystem = { state.selectNumberSystem(it) }
                )

                RollButton(
                    onClick = { state.roll() }
                )
            }
        }
    }
}
