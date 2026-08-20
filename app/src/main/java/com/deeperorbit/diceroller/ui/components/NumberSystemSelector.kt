package com.deeperorbit.diceroller.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeperorbit.diceroller.domain.NumberSystem

/**
 * Segmented Control for switching between Western, Eastern, and Roman number systems.
 */
@Composable
fun NumberSystemSelector(
    selectedSystem: NumberSystem,
    onSelectSystem: (NumberSystem) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background == Color(0xFF000000)

    Row(
        modifier = modifier.testTag("number_system_selector"),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NumberSystem.values().forEachIndexed { index, system ->
            val isSelected = selectedSystem == system

            val targetContainerColor = if (isDark) {
                if (isSelected) Color(0xFFD6D6D4) else Color(0xFF333333)
            } else {
                if (isSelected) Color(0xFF5A5A5A) else Color(0xFFD6D6D4)
            }

            val targetContentColor = if (isDark) {
                if (isSelected) Color(0xFF191919) else Color(0xFFEDEDED)
            } else {
                if (isSelected) Color.White else Color(0xFF1E1E1E)
            }

            val containerColor by animateColorAsState(
                targetValue = targetContainerColor,
                label = "seg_bg"
            )
            val contentColor by animateColorAsState(
                targetValue = targetContentColor,
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
                    NumberSystemBadge(
                        system = system,
                        isSelected = isSelected,
                        isDarkTheme = isDark,
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
 * Numeral system badge displaying "12", "۱۲", or "IV".
 */
@Composable
fun NumberSystemBadge(
    system: NumberSystem,
    isSelected: Boolean,
    isDarkTheme: Boolean,
    size: Dp,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isDarkTheme) {
        if (isSelected) Color(0xFF191919) else Color.Transparent
    } else {
        if (isSelected) Color.White else Color.Transparent
    }

    val textColor = if (isDarkTheme) {
        if (isSelected) Color(0xFFD6D6D4) else Color(0xFFEDEDED)
    } else {
        if (isSelected) Color(0xFF5A5A5A) else Color(0xFF1E1E1E)
    }

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
