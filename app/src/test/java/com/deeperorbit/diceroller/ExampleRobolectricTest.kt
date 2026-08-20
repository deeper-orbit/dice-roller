package com.deeperorbit.diceroller

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.deeperorbit.diceroller.domain.ThemeMode
import com.deeperorbit.diceroller.ui.DiceRollerScreen
import com.deeperorbit.diceroller.ui.MainHomeScreen
import com.deeperorbit.diceroller.ui.SettingsScreen
import com.deeperorbit.diceroller.ui.theme.MyApplicationTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Dice Roller", appName)
  }

  @Test
  fun `initial state shows placeholder and roll button`() {
    composeTestRule.setContent {
      MyApplicationTheme {
        DiceRollerScreen()
      }
    }

    composeTestRule.onNodeWithText("Try rolling dice").assertIsDisplayed()
    composeTestRule.onNodeWithTag("roll_dice_button").assertIsDisplayed()
  }

  @Test
  fun `main home screen displays tabs and switches to maybe`() {
    composeTestRule.setContent {
      MyApplicationTheme {
        MainHomeScreen(onNavigateToSettings = {})
      }
    }

    composeTestRule.onNodeWithTag("top_tab_selector").assertIsDisplayed()
    composeTestRule.onNodeWithTag("tab_dice").assertIsDisplayed()
    composeTestRule.onNodeWithTag("tab_maybe").assertIsDisplayed()
    composeTestRule.onNodeWithTag("settings_button").assertIsDisplayed()

    // Switch to Maybe tab
    composeTestRule.onNodeWithTag("tab_maybe").performClick()
    composeTestRule.onNodeWithTag("maybe_flip_button").assertIsDisplayed()
    composeTestRule.onNodeWithText("Try your luck").assertIsDisplayed()
  }

  @Test
  fun `settings screen displays theme item`() {
    composeTestRule.setContent {
      MyApplicationTheme(themeMode = ThemeMode.DARK) {
        SettingsScreen(
          currentThemeMode = ThemeMode.DARK,
          onThemeModeChange = {},
          onNavigateBack = {}
        )
      }
    }

    composeTestRule.onNodeWithTag("settings_back_button").assertIsDisplayed()
    composeTestRule.onNodeWithTag("settings_item_theme").assertIsDisplayed()
    composeTestRule.onNodeWithText("Theme").assertIsDisplayed()
    composeTestRule.onNodeWithText("Switch To Light or Dark Mode.").assertIsDisplayed()
  }
}
