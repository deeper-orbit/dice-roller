package com.deeperorbit.diceroller.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.deeperorbit.diceroller.domain.MainTab
import com.deeperorbit.diceroller.ui.components.TopTabSelector

/**
 * Main Home Screen coordinating top tabs (Dice vs Maybe) and the settings entry.
 */
@Composable
fun MainHomeScreen(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(MainTab.DICE) }
    val diceState = rememberDiceRollerState()
    val maybeState = rememberMaybeState()

    val isDark = MaterialTheme.colorScheme.background == Color(0xFF000000)
    val iconColor = if (isDark) Color(0xFFEDEDED) else Color(0xFF191919)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Main Content Area (Animated between Dice and Maybe)
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                },
                modifier = Modifier.fillMaxSize(),
                label = "tab_content_transition"
            ) { tab ->
                when (tab) {
                    MainTab.DICE -> {
                        DiceRollerScreen(
                            state = diceState,
                            onNavigateToSettings = onNavigateToSettings
                        )
                    }
                    MainTab.MAYBE -> {
                        MaybeScreen(
                            state = maybeState
                        )
                    }
                }
            }

            // Top Bar with Tab Selector and Settings Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Invisible balance spacer on left to center the tab selector perfectly
                IconButton(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.padding(4.dp)
                ) { }

                // Center Top Tab Switcher
                TopTabSelector(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                // Right Settings Button
                IconButton(
                    onClick = onNavigateToSettings,
                    modifier = Modifier.testTag("settings_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = iconColor
                    )
                }
            }
        }
    }
}
