package com.deeperorbit.diceroller

import com.deeperorbit.diceroller.domain.DiceRoller
import com.deeperorbit.diceroller.domain.MaybeEngine
import com.deeperorbit.diceroller.domain.MaybeOutcome
import com.deeperorbit.diceroller.domain.NumberSystem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DomainUnitTest {

    @Test
    fun `western number system formats correctly`() {
        assertEquals("1", NumberSystem.WESTERN.format(1))
        assertEquals("6", NumberSystem.WESTERN.format(6))
    }

    @Test
    fun `eastern number system formats correctly`() {
        assertEquals("۱", NumberSystem.EASTERN.format(1))
        assertEquals("۳", NumberSystem.EASTERN.format(3))
        assertEquals("۶", NumberSystem.EASTERN.format(6))
    }

    @Test
    fun `roman number system formats correctly`() {
        assertEquals("I", NumberSystem.ROMAN.format(1))
        assertEquals("IV", NumberSystem.ROMAN.format(4))
        assertEquals("VI", NumberSystem.ROMAN.format(6))
    }

    @Test
    fun `dice roller produces numbers within valid range`() {
        for (i in 1..100) {
            val roll = DiceRoller.roll()
            assertTrue(roll in 1..6)
        }
    }

    @Test
    fun `next intermediate number avoids consecutive duplicates`() {
        for (i in 1..50) {
            val prev = 3
            val next = DiceRoller.nextIntermediateNumber(prev)
            assertTrue(next != prev)
            assertTrue(next in 1..6)
        }
    }

    @Test
    fun `maybe outcome opposite returns inverted state`() {
        assertEquals(MaybeOutcome.NO, MaybeOutcome.YES.opposite())
        assertEquals(MaybeOutcome.YES, MaybeOutcome.NO.opposite())
    }

    @Test
    fun `maybe engine produces valid outcome`() {
        val outcomes = mutableSetOf<MaybeOutcome>()
        for (i in 1..50) {
            outcomes.add(MaybeEngine.decide())
        }
        assertTrue(outcomes.contains(MaybeOutcome.YES) || outcomes.contains(MaybeOutcome.NO))
    }
}
