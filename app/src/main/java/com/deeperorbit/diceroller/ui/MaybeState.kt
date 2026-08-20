package com.deeperorbit.diceroller.ui

import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import com.deeperorbit.diceroller.domain.MaybeEngine
import com.deeperorbit.diceroller.domain.MaybeOutcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * State holder for the "Maybe" / "Try your luck" screen.
 */
@Stable
class MaybeState(
    private val scope: CoroutineScope,
    private val view: View
) {
    var currentOutcome by mutableStateOf<MaybeOutcome?>(null)
    var displayedOutcome by mutableStateOf(MaybeOutcome.YES)
    var isFlipping by mutableStateOf(false)

    val flipRotation: Animatable<Float, AnimationVector1D> = Animatable(0f)
    val scaleAnim: Animatable<Float, AnimationVector1D> = Animatable(1f)
    val colorTransitionProgress: Animatable<Float, AnimationVector1D> = Animatable(0f)

    fun flip() {
        if (isFlipping) return
        isFlipping = true
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

        scope.launch {
            colorTransitionProgress.animateTo(0f, tween(150))
            val totalSteps = MaybeEngine.DEFAULT_FLIP_STEPS
            var lastOutcome = displayedOutcome

            launch {
                scaleAnim.animateTo(
                    targetValue = 0.88f,
                    animationSpec = tween(100, easing = FastOutSlowInEasing)
                )
                scaleAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }

            for (step in 1..totalSteps) {
                val nextOutcome = if (step == totalSteps) {
                    MaybeEngine.decide()
                } else {
                    lastOutcome.opposite()
                }

                displayedOutcome = nextOutcome
                lastOutcome = nextOutcome

                if (step == totalSteps) {
                    currentOutcome = nextOutcome
                }

                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

                val stepDuration = MaybeEngine.flipDurationMillis(step)
                flipRotation.animateTo(
                    targetValue = flipRotation.value + 180f,
                    animationSpec = tween(
                        durationMillis = stepDuration,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            isFlipping = false
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)

            colorTransitionProgress.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }
}

@Composable
fun rememberMaybeState(
    scope: CoroutineScope = rememberCoroutineScope(),
    view: View = LocalView.current
): MaybeState {
    return remember(scope, view) {
        MaybeState(scope = scope, view = view)
    }
}
