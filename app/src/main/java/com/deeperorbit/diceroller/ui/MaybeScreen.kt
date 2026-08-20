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
import com.deeperorbit.diceroller.domain.MaybeOutcome
import com.deeperorbit.diceroller.ui.components.MaybeButton
import com.deeperorbit.diceroller.ui.components.MaybeDisplay

/**
 * Maybe / Oracle screen with interactive Thumb Up (Yes) and Thumb Down (No) animations
 * and responsive Green (Yes) / Red (No) dynamic background transitions.
 */
@Composable
fun MaybeScreen(
    modifier: Modifier = Modifier,
    state: MaybeState = rememberMaybeState()
) {
    val defaultBg = MaterialTheme.colorScheme.background
    val isDark = defaultBg == Color(0xFF000000)

    // Compute dynamic background color based on YES (Green) or NO (Red) result
    val targetBgColor = when {
        state.isFlipping -> defaultBg
        state.currentOutcome == MaybeOutcome.YES -> {
            if (isDark) Color(0xFF091F11) else Color(0xFFE8F5E9)
        }
        state.currentOutcome == MaybeOutcome.NO -> {
            if (isDark) Color(0xFF260A0D) else Color(0xFFFFEBEE)
        }
        else -> defaultBg
    }

    val animatedBgColor by animateColorAsState(
        targetValue = targetBgColor,
        animationSpec = tween(durationMillis = 400),
        label = "maybe_screen_bg"
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
            // Center Oracle Display Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (state.currentOutcome == null && !state.isFlipping) {
                    Text(
                        text = "Try your luck",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = placeholderColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .testTag("maybe_placeholder_text")
                            .padding(bottom = 64.dp)
                    )
                } else {
                    val activeOutcome = if (state.isFlipping) {
                        state.displayedOutcome
                    } else {
                        state.currentOutcome ?: MaybeOutcome.YES
                    }

                    MaybeDisplay(
                        activeOutcome = activeOutcome,
                        isFlipping = state.isFlipping,
                        rotation = state.flipRotation.value,
                        scale = state.scaleAnim.value,
                        onCardClick = { state.flip() }
                    )
                }
            }

            // Bottom Action Controls
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 36.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MaybeButton(
                    onClick = { state.flip() }
                )
            }
        }
    }
}
