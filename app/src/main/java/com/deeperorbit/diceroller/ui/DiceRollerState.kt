package com.deeperorbit.diceroller.ui

import android.content.Context
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.deeperorbit.diceroller.domain.DiceRoller
import com.deeperorbit.diceroller.domain.NumberSystem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * State holder for the Dice Roller screen, managing animation sequences,
 * shape morphing parameters, number system selections, and special 6 roll events.
 */
@Stable
class DiceRollerState(
    private val scope: CoroutineScope,
    private val view: View,
    private val context: Context
) {
    var selectedNumberSystem by mutableStateOf(NumberSystem.WESTERN)
    var currentNumber by mutableStateOf<Int?>(null)
    var displayedNumber by mutableIntStateOf(1)
    var startShapeNumber by mutableIntStateOf(1)
    var targetShapeNumber by mutableIntStateOf(1)
    var isRolling by mutableStateOf(false)
    var isSixInverted by mutableStateOf(false)

    val morphProgress: Animatable<Float, AnimationVector1D> = Animatable(1f)
    val scaleAnim: Animatable<Float, AnimationVector1D> = Animatable(1f)

    fun selectNumberSystem(system: NumberSystem) {
        selectedNumberSystem = system
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }

    fun roll() {
        if (isRolling) return
        isRolling = true
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

        scope.launch {
            val totalSteps = DiceRoller.DEFAULT_ANIMATION_STEPS
            var lastNum = targetShapeNumber

            launch {
                scaleAnim.animateTo(
                    targetValue = 0.93f,
                    animationSpec = tween(90, easing = FastOutSlowInEasing)
                )
                scaleAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            }

            var finalRolledNumber = 1

            for (step in 1..totalSteps) {
                val nextNum = if (step == totalSteps) {
                    DiceRoller.roll().also { finalRolledNumber = it }
                } else {
                    DiceRoller.nextIntermediateNumber(lastNum)
                }

                startShapeNumber = lastNum
                targetShapeNumber = nextNum
                displayedNumber = nextNum
                lastNum = nextNum

                if (step == totalSteps) {
                    currentNumber = nextNum
                }

                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

                val stepDuration = DiceRoller.stepDurationMillis(step)
                morphProgress.snapTo(0f)
                morphProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = stepDuration,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            isRolling = false

            if (finalRolledNumber == 6) {
                // Special celebratory vibration on rolling 6
                HapticHelper.performSpecialSixVibration(context, view)

                // Temporarily invert background for exactly 1 second
                launch {
                    isSixInverted = true
                    delay(1000)
                    isSixInverted = false
                }
            } else {
                view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            }
        }
    }
}

@Composable
fun rememberDiceRollerState(
    scope: CoroutineScope = rememberCoroutineScope(),
    view: View = LocalView.current,
    context: Context = LocalContext.current
): DiceRollerState {
    return remember(scope, view, context) {
        DiceRollerState(scope = scope, view = view, context = context)
    }
}
