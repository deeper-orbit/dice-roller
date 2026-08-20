package com.deeperorbit.diceroller.domain

/**
 * Supported numeral systems for formatting rolled dice numbers.
 */
enum class NumberSystem(val label: String) {
    WESTERN("Western"),
    EASTERN("Eastern"),
    ROMAN("Roman");

    fun format(number: Int): String {
        return when (this) {
            WESTERN -> number.toString()
            EASTERN -> when (number) {
                1 -> "۱"
                2 -> "۲"
                3 -> "۳"
                4 -> "۴"
                5 -> "۵"
                6 -> "۶"
                else -> number.toString()
            }
            ROMAN -> when (number) {
                1 -> "I"
                2 -> "II"
                3 -> "III"
                4 -> "IV"
                5 -> "V"
                6 -> "VI"
                else -> number.toString()
            }
        }
    }
}
