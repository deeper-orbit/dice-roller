package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.ui.DiceRollerScreen
import com.example.ui.theme.MyApplicationTheme
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
  fun `clicking roll button initiates roll`() {
    composeTestRule.setContent {
      MyApplicationTheme {
        DiceRollerScreen()
      }
    }

    composeTestRule.onNodeWithTag("roll_dice_button").performClick()
    composeTestRule.onNodeWithTag("dice_display").assertIsDisplayed()
  }
}

