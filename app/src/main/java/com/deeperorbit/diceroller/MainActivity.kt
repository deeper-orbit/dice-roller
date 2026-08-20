package com.deeperorbit.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.deeperorbit.diceroller.domain.PreferencesManager
import com.deeperorbit.diceroller.domain.ThemeMode
import com.deeperorbit.diceroller.ui.MainHomeScreen
import com.deeperorbit.diceroller.ui.SettingsScreen
import com.deeperorbit.diceroller.ui.theme.MyApplicationTheme

enum class AppScreen {
    HOME,
    SETTINGS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val preferencesManager = remember { PreferencesManager(context) }
            var themeMode by remember { mutableStateOf(preferencesManager.themeMode) }
            var currentScreen by remember { mutableStateOf(AppScreen.HOME) }

            MyApplicationTheme(themeMode = themeMode) {
                if (currentScreen == AppScreen.SETTINGS) {
                    BackHandler {
                        currentScreen = AppScreen.HOME
                    }
                }

                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        if (targetState == AppScreen.SETTINGS) {
                            (slideInHorizontally { it } + fadeIn())
                                .togetherWith(slideOutHorizontally { -it } + fadeOut())
                        } else {
                            (slideInHorizontally { -it } + fadeIn())
                                .togetherWith(slideOutHorizontally { it } + fadeOut())
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        AppScreen.HOME -> {
                            MainHomeScreen(
                                onNavigateToSettings = {
                                    currentScreen = AppScreen.SETTINGS
                                }
                            )
                        }
                        AppScreen.SETTINGS -> {
                            SettingsScreen(
                                currentThemeMode = themeMode,
                                onThemeModeChange = { newMode ->
                                    themeMode = newMode
                                    preferencesManager.themeMode = newMode
                                },
                                onNavigateBack = {
                                    currentScreen = AppScreen.HOME
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F0)
@Composable
fun MainHomePreview() {
    MyApplicationTheme(themeMode = ThemeMode.LIGHT) {
        MainHomeScreen(onNavigateToSettings = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MainHomeDarkPreview() {
    MyApplicationTheme(themeMode = ThemeMode.DARK) {
        MainHomeScreen(onNavigateToSettings = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SettingsDarkPreview() {
    MyApplicationTheme(themeMode = ThemeMode.DARK) {
        SettingsScreen(
            currentThemeMode = ThemeMode.DARK,
            onThemeModeChange = {},
            onNavigateBack = {}
        )
    }
}
