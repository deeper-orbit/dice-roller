package com.deeperorbit.diceroller.domain

import kotlin.random.Random

/**
 * Engine for the "Maybe" (Yes / No / Try your luck) oracle.
 */
object MaybeEngine {
    const val DEFAULT_FLIP_STEPS = 12

    fun decide(): MaybeOutcome {
        return if (Random.nextBoolean()) MaybeOutcome.YES else MaybeOutcome.NO
    }

    /**
     * Determines duration in milliseconds for each flip step in deceleration.
     */
    fun flipDurationMillis(stepIndex: Int): Int {
        return when (stepIndex) {
            in 1..8 -> 65
            9 -> 90
            10 -> 120
            11 -> 160
            else -> 220
        }
    }
}
