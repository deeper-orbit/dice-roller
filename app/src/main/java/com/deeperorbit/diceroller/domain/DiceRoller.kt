package com.deeperorbit.diceroller.domain

import kotlin.random.Random

/**
 * Domain engine for dice rolling mechanics and sequence generation.
 */
object DiceRoller {
    const val MIN_VALUE = 1
    const val MAX_VALUE = 6
    const val DEFAULT_ANIMATION_STEPS = 10

    /**
     * Generates a random dice number between 1 and 6.
     */
    fun roll(): Int = Random.nextInt(MIN_VALUE, MAX_VALUE + 1)

    /**
     * Generates a sequential roll step avoiding consecutive duplicates.
     */
    fun nextIntermediateNumber(previousNumber: Int): Int {
        var candidate = roll()
        while (candidate == previousNumber) {
            candidate = roll()
        }
        return candidate
    }

    /**
     * Material 3 animation duration in milliseconds for a specific step in the roll sequence.
     */
    fun stepDurationMillis(stepIndex: Int): Int {
        return when (stepIndex) {
            in 1..7 -> 70
            8 -> 95
            9 -> 130
            else -> 170
        }
    }
}
