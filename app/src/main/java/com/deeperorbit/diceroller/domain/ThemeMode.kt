package com.deeperorbit.diceroller.domain

/**
 * Supported display themes in the app.
 */
enum class ThemeMode(val title: String, val subtitle: String) {
    SYSTEM("System Default", "Follow system settings"),
    LIGHT("Light Mode", "Always use light appearance"),
    DARK("Dark Mode", "Always use dark appearance");
}
