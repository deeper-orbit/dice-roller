package com.deeperorbit.diceroller.ui

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.view.View

/**
 * Haptics and custom vibration manager for dice roll outcomes.
 */
object HapticHelper {

    /**
     * Triggers a special celebratory vibration pattern when rolling a 6.
     */
    fun performSpecialSixVibration(context: Context, view: View) {
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            if (vibrator != null && vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Celebratory double-pulse waveform for rolling 6
                    val timings = longArrayOf(0, 70, 80, 140, 90, 220)
                    val amplitudes = intArrayOf(0, 180, 0, 240, 0, 255)
                    val effect = VibrationEffect.createWaveform(timings, amplitudes, -1)
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(longArrayOf(0, 80, 80, 160, 80, 220), -1)
                }
            } else {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
        } catch (e: Exception) {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        }
    }
}
