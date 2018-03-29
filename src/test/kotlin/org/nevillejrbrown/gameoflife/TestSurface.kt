package org.nevillejrbrown.gameoflife

import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestSurface {
    @Test
    fun `test board set and get position`() {
        val surface = Surface(10, 10)
        assertTrue(surface.getPointState(2,4) == State.DEAD)
    }

    @Test fun `test generate having one adjacent cell kills through under-population`() {
        val surface = Surface(10, 10)
        surface.setPointState(StateChange(4,4, State.ALIVE))
        surface.setPointState(StateChange(5, 5, State.ALIVE))

        surface.generate()

        assertTrue(surface.getPointState(5,5) == State.DEAD)
        assertTrue(surface.getPointState(4,4) == State.DEAD)
    }

    @Test fun `test generate having two adjacent cells doesn't kill through under-population`() {
        val surface = Surface(10, 10)
        surface.setPointState(StateChange(4,4, State.ALIVE))
        surface.setPointState(StateChange(4,5, State.ALIVE))
        surface.setPointState(StateChange(5, 5, State.ALIVE))

        surface.generate()

        assertTrue(surface.getPointState(4,4) == State.ALIVE)
        assertTrue(surface.getPointState(4,5) == State.ALIVE)
        assertTrue(surface.getPointState(5,5) == State.ALIVE)
    }

    @Test fun `test generate having two adjacent cells, wrapped around the side, doesn't kill through under-population`() {
        val surface = Surface(10, 10)
        surface.setPointState(StateChange(4,0, State.ALIVE))
        surface.setPointState(StateChange(5,0, State.ALIVE))
        surface.setPointState(StateChange(5, 9, State.ALIVE))

        surface.generate()

        assertTrue(surface.getPointState(4,0) == State.ALIVE)
        assertTrue(surface.getPointState(5,0) == State.ALIVE)
        assertTrue(surface.getPointState(5,9) == State.ALIVE)
    }
    @Test fun `test generate having two adjacent cells, wrapped around the top, doesn't kill through under-population`() {
        val surface = Surface(10, 10)
        surface.setPointState(StateChange(0,8, State.ALIVE))
        surface.setPointState(StateChange(0,9, State.ALIVE))
        surface.setPointState(StateChange(9, 9, State.ALIVE))

        surface.generate()

        assertTrue(surface.getPointState(0,8) == State.ALIVE)
        assertTrue(surface.getPointState(0,9) == State.ALIVE)
        assertTrue(surface.getPointState(9,9) == State.ALIVE)
    }

    @Test fun `test generate having more than three  adjacent cells kills through over population`() {
        val surface = Surface(10, 10)
        surface.setPointState(StateChange(0,8, State.ALIVE))
        surface.setPointState(StateChange(0,9, State.ALIVE))
        surface.setPointState(StateChange(1,8, State.ALIVE))
        surface.setPointState(StateChange(9, 9, State.ALIVE))
        surface.setPointState(StateChange(9, 8, State.ALIVE))

        surface.generate()

        assertTrue(surface.getPointState(0,8) == State.DEAD)
        assertTrue(surface.getPointState(0,9) == State.DEAD)
        assertTrue(surface.getPointState(1,8) == State.ALIVE)
        assertTrue(surface.getPointState(9,9) == State.ALIVE)
        assertTrue(surface.getPointState(9,8) == State.ALIVE)
    }
    @Test fun `test generate empty cell having exactly three adjacent cells comes alive`() {
        val surface = Surface(10, 10)
        surface.setPointState(StateChange(2,4, State.ALIVE))
        surface.setPointState(StateChange(3,4, State.ALIVE))
        surface.setPointState(StateChange(4,4, State.ALIVE))

        surface.generate()

        assertTrue(surface.getPointState(2,4) == State.DEAD)
        assertTrue(surface.getPointState(3,4) == State.ALIVE)
        assertTrue(surface.getPointState(4,4) == State.DEAD)
        assertTrue(surface.getPointState(3,3) == State.ALIVE)
        assertTrue(surface.getPointState(3,5) == State.ALIVE)
    }


}