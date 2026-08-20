package com.deeperorbit.diceroller.domain

/**
 * Outcome for the "Maybe" / "Try your luck" decision oracle.
 */
enum class MaybeOutcome(val label: String, val isPositive: Boolean) {
    YES("Yes", true),
    NO("No", false);

    fun opposite(): MaybeOutcome = if (this == YES) NO else YES
}
