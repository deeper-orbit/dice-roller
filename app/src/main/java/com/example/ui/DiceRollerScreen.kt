package com.example.ui

import android.view.HapticFeedbackConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ButtonBackground
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.DiceBlack
import com.example.ui.theme.TextPlaceholder
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.WhiteText
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class NumberSystem(val label: String) {
    WESTERN("Western"),
    EASTERN("Eastern"),
    ROMAN("Roman");

    fun format(number: Int): String {
        return when (this) {
            WESTERN -> number.toString()
            EASTERN -> when (number) {
                1 -> "۱"
                2 -> "۲"
                3 -> "۳"
                4 -> "۴"
                5 -> "۵"
                6 -> "۶"
                else -> number.toString()
            }
            ROMAN -> when (number) {
                1 -> "I"
                2 -> "II"
                3 -> "III"
                4 -> "IV"
                5 -> "V"
                6 -> "VI"
                else -> number.toString()
            }
        }
    }
}

@Composable
fun DiceRollerScreen(
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val scope = rememberCoroutineScope()

    // State for number system: Western, Eastern, Roman
    var selectedNumberSystem by remember { mutableStateOf(NumberSystem.WESTERN) }

    // State for rolled number: null = initial "Try Roll a Dice" state, 1..6 = rolled state
    var currentNumber by remember { mutableStateOf<Int?>(null) }
    var displayedNumber by remember { mutableIntStateOf(1) }
    var startShapeNumber by remember { mutableIntStateOf(1) }
    var targetShapeNumber by remember { mutableIntStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    // Google Morph Animation Progress (0f -> 1f)
    val morphProgress = remember { Animatable(1f) }

    // Subtle scale animation on dice
    val scaleAnim = remember { Animatable(1f) }

    fun rollDice() {
        if (isRolling) return
        isRolling = true
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)

        scope.launch {
            val totalSteps = 10
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

            for (step in 1..totalSteps) {
                val nextNum = if (step == totalSteps) {
                    Random.nextInt(1, 7)
                } else {
                    var candidate = Random.nextInt(1, 7)
                    while (candidate == lastNum) {
                        candidate = Random.nextInt(1, 7)
                    }
                    candidate
                }

                startShapeNumber = lastNum
                targetShapeNumber = nextNum
                displayedNumber = nextNum
                lastNum = nextNum

                if (step == totalSteps) {
                    currentNumber = nextNum
                }

                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)

                // Google's Material 3 standard morph duration and easing
                val stepDuration = when (step) {
                    in 1..7 -> 70
                    8 -> 95
                    9 -> 130
                    else -> 170
                }

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
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasBackground),
        color = CanvasBackground
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Main Center View
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (currentNumber == null && !isRolling) {
                    // Initial State: "Try rolling dice"
                    Text(
                        text = "Try rolling dice",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextPlaceholder,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .testTag("placeholder_text")
                            .padding(bottom = 64.dp)
                    )
                } else {
                    // Dice Shape with Number
                    val activeNumber = if (isRolling) displayedNumber else (currentNumber ?: 1)
                    val formattedText = selectedNumberSystem.format(activeNumber)

                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .scale(scaleAnim.value)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                rollDice()
                            }
                            .testTag("dice_display"),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (isRolling) {
                                drawMorphDiceShape(
                                    startNumber = startShapeNumber,
                                    endNumber = targetShapeNumber,
                                    progress = morphProgress.value,
                                    color = DiceBlack
                                )
                            } else {
                                drawDiceShape(
                                    number = activeNumber,
                                    color = DiceBlack
                                )
                            }
                        }

                        // Display the white number formatted according to the selected number system
                        val fontSize = when {
                            formattedText.length >= 3 -> 54.sp
                            formattedText.length == 2 -> 68.sp
                            else -> 82.sp
                        }

                        Text(
                            text = formattedText,
                            color = WhiteText,
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
            }

            // Bottom Controls Area: Segmented Button + Roll Button
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 36.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Segmented Button Row: Western | Eastern | Roman
                NumberSystemSegmentedControl(
                    selectedSystem = selectedNumberSystem,
                    onSelectSystem = {
                        selectedNumberSystem = it
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    },
                    modifier = Modifier.testTag("number_system_selector")
                )

                // Large Pill Roll a Dice Button matching wireframe
                Button(
                    onClick = { rollDice() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonBackground,
                        contentColor = TextPrimary
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    modifier = Modifier
                        .testTag("roll_dice_button")
                        .height(58.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Black filled circle badge with rolling dice icon
                        DiceActionBadge(
                            circleColor = Color(0xFF191919),
                            diceColor = Color.White,
                            size = 28.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Roll a Dice",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                    }
                }
            }
        }
    }
}

/**
 * Custom styled Material 3 Segmented Control for Western, Eastern, Roman
 */
@Composable
fun NumberSystemSegmentedControl(
    selectedSystem: NumberSystem,
    onSelectSystem: (NumberSystem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NumberSystem.values().forEachIndexed { index, system ->
            val isSelected = selectedSystem == system

            val containerColor by animateColorAsState(
                targetValue = if (isSelected) Color(0xFF5A5A5A) else Color(0xFFD6D6D4),
                label = "seg_bg"
            )
            val contentColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color(0xFF1E1E1E),
                label = "seg_content"
            )

            val shape = when (index) {
                0 -> RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp, topEnd = 6.dp, bottomEnd = 6.dp)
                NumberSystem.values().lastIndex -> RoundedCornerShape(topStart = 6.dp, bottomStart = 6.dp, topEnd = 20.dp, bottomEnd = 20.dp)
                else -> RoundedCornerShape(6.dp)
            }

            Box(
                modifier = Modifier
                    .clip(shape)
                    .background(containerColor)
                    .clickable { onSelectSystem(system) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // System specific badge (Western: "12", Eastern: "۱۲", Roman: "IV")
                    NumberSystemBadge(
                        system = system,
                        isSelected = isSelected,
                        size = 20.dp
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = system.label,
                        color = contentColor,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Dedicated Badge for each Number System (Western "12", Eastern "۱۲", Roman "IV")
 */
@Composable
fun NumberSystemBadge(
    system: NumberSystem,
    isSelected: Boolean,
    size: Dp,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) Color.White else Color.Transparent
    val textColor = if (isSelected) Color(0xFF5A5A5A) else Color(0xFF1E1E1E)
    val badgeSymbol = when (system) {
        NumberSystem.WESTERN -> "12"
        NumberSystem.EASTERN -> "۱۲"
        NumberSystem.ROMAN -> "IV"
    }

    Box(
        modifier = modifier
            .size(size)
            .background(bgColor, CircleShape)
            .then(
                if (!isSelected) Modifier.border(1.2.dp, textColor, CircleShape) else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = badgeSymbol,
            color = textColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Relevant Dice Vector Badge for Roll button
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

            // Draw rounded die face
            drawRoundRect(
                color = diceColor,
                topLeft = androidx.compose.ui.geometry.Offset(0f, 0f),
                size = androidx.compose.ui.geometry.Size(w, h),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(corner, corner),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = w * 0.12f)
            )

            // Draw 5 standard dice pips (dots)
            val dotRadius = w * 0.08f
            val dotColor = diceColor
            // Center dot
            drawCircle(color = dotColor, radius = dotRadius, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f))
            // 4 Corner dots
            drawCircle(color = dotColor, radius = dotRadius, center = androidx.compose.ui.geometry.Offset(w * 0.28f, h * 0.28f))
            drawCircle(color = dotColor, radius = dotRadius, center = androidx.compose.ui.geometry.Offset(w * 0.72f, h * 0.28f))
            drawCircle(color = dotColor, radius = dotRadius, center = androidx.compose.ui.geometry.Offset(w * 0.28f, h * 0.72f))
            drawCircle(color = dotColor, radius = dotRadius, center = androidx.compose.ui.geometry.Offset(w * 0.72f, h * 0.72f))
        }
    }
}

/**
 * Optical centering adjustment for numbers inside geometric shapes
 */
private fun Modifier.offsetNumberForGeometry(number: Int): Modifier {
    return when (number) {
        3 -> this.graphicsLayer { translationY = 8f }
        2 -> this.graphicsLayer { translationY = -2f }
        5 -> this.graphicsLayer { translationY = 4f }
        else -> this
    }
}


