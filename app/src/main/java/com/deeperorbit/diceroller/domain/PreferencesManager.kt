package com.deeperorbit.diceroller.domain

import android.content.Context
import android.content.SharedPreferences

/**
 * Local persistence manager for user preferences (Theme, Number System, Haptics).
 */
class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("dice_roller_prefs", Context.MODE_PRIVATE)

    var themeMode: ThemeMode
        get() {
            val name = prefs.getString(KEY_THEME_MODE, ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name
            return try {
                ThemeMode.valueOf(name)
            } catch (e: Exception) {
                ThemeMode.SYSTEM
            }
        }
        set(value) {
            prefs.edit().putString(KEY_THEME_MODE, value.name).apply()
        }

    var selectedNumberSystem: NumberSystem
        get() {
            val name = prefs.getString(KEY_NUMBER_SYSTEM, NumberSystem.WESTERN.name) ?: NumberSystem.WESTERN.name
            return try {
                NumberSystem.valueOf(name)
            } catch (e: Exception) {
                NumberSystem.WESTERN
            }
        }
        set(value) {
            prefs.edit().putString(KEY_NUMBER_SYSTEM, value.name).apply()
        }

    companion object {
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_NUMBER_SYSTEM = "number_system"
    }
}
