package com.deeperorbit.diceroller.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeperorbit.diceroller.domain.MainTab

/**
 * Top Segmented Tab Switcher between "Dice" and "Maybe".
 */
@Composable
fun TopTabSelector(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background == Color(0xFF000000)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (isDark) Color(0xFF1E1E1E) else Color(0xFFE4E4E2))
            .padding(4.dp)
            .testTag("top_tab_selector"),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainTab.values().forEach { tab ->
            val isSelected = selectedTab == tab

            val targetContainerColor = if (isDark) {
                if (isSelected) Color(0xFFD6D6D4) else Color.Transparent
            } else {
                if (isSelected) Color(0xFF5A5A5A) else Color.Transparent
            }

            val targetContentColor = if (isDark) {
                if (isSelected) Color(0xFF111111) else Color(0xFFA0A0A0)
            } else {
                if (isSelected) Color.White else Color(0xFF555555)
            }

            val containerColor by animateColorAsState(
                targetValue = targetContainerColor,
                label = "tab_bg"
            )
            val contentColor by animateColorAsState(
                targetValue = targetContentColor,
                label = "tab_fg"
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(containerColor)
                    .clickable { onTabSelected(tab) }
                    .padding(horizontal = 18.dp, vertical = 8.dp)
                    .testTag("tab_${tab.name.lowercase()}"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (tab == MainTab.DICE) Icons.Default.Casino else Icons.Default.AutoAwesome,
                        contentDescription = tab.title,
                        tint = contentColor,
                        modifier = Modifier.size(17.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = tab.title,
                        color = contentColor,
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                    )
                }
            }
        }
    }
}
