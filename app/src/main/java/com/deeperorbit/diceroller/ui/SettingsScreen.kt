package com.deeperorbit.diceroller.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deeperorbit.diceroller.domain.ThemeMode

/**
 * Settings Screen with search bar and Theme customization sheet matching the design.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentThemeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showThemeDialog by remember { mutableStateOf(false) }

    val isDark = MaterialTheme.colorScheme.background == Color(0xFF000000)
    val bgColor = MaterialTheme.colorScheme.background
    val searchBarBg = if (isDark) Color(0xFF242424) else Color(0xFFE4E4E2)
    val cardBg = if (isDark) Color(0xFF181818) else Color(0xFFFFFFFF)
    val textColor = MaterialTheme.colorScheme.onBackground
    val textSecondary = if (isDark) Color(0xFFA0A0A0) else Color(0xFF707070)

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor),
        color = bgColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Top Search Bar (matching screenshot layout)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(CircleShape)
                    .background(searchBarBg)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("settings_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = textColor
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Input text",
                            color = textSecondary,
                            fontSize = 16.sp
                        )
                    }

                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = textColor,
                            fontSize = 16.sp
                        ),
                        cursorBrush = SolidColor(textColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("settings_search_input")
                    )
                }

                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { searchQuery = "" },
                        modifier = Modifier.testTag("settings_clear_search")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search",
                            tint = textSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Settings Items List
            val matchesTheme = "theme".contains(searchQuery.trim().lowercase()) ||
                    "dark".contains(searchQuery.trim().lowercase()) ||
                    "light".contains(searchQuery.trim().lowercase()) ||
                    searchQuery.isBlank()

            if (matchesTheme) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(cardBg)
                        .clickable { showThemeDialog = true }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                        .testTag("settings_item_theme")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DarkMode,
                            contentDescription = "Theme Icon",
                            tint = textColor,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Theme",
                                color = textColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Switch To Light or Dark Mode.",
                                color = textSecondary,
                                fontSize = 13.sp
                            )
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Open theme selector",
                            tint = textSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No settings found",
                        color = textSecondary,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }

    // Modal Bottom Sheet for Theme Selector
    if (showThemeDialog) {
        val sheetState = rememberModalBottomSheetState()

        ModalBottomSheet(
            onDismissRequest = { showThemeDialog = false },
            sheetState = sheetState,
            containerColor = if (isDark) Color(0xFF1E1E1E) else Color.White,
            dragHandle = null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Text(
                    text = "Choose Theme",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ThemeMode.values().forEach { mode ->
                    val isSelected = currentThemeMode == mode

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                onThemeModeChange(mode)
                                showThemeDialog = false
                            }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val icon = when (mode) {
                            ThemeMode.LIGHT -> Icons.Default.LightMode
                            ThemeMode.DARK -> Icons.Default.DarkMode
                            ThemeMode.SYSTEM -> Icons.Default.SettingsBrightness
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = mode.title,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else textSecondary,
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = mode.title,
                                fontSize = 16.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = textColor
                            )
                            Text(
                                text = mode.subtitle,
                                fontSize = 13.sp,
                                color = textSecondary
                            )
                        }

                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                onThemeModeChange(mode)
                                showThemeDialog = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (isDark) Color.White else Color(0xFF191919)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
