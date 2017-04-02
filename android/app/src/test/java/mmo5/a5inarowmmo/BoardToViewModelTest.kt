package mmo5.a5inarowmmo

import org.junit.Assert.*
import org.junit.Test

class BoardToViewModelTest {


    @Test fun `getHorizontalLines`() {
        val tested = BoardToViewModel(100, 100, 1, 0)
        val lines = tested.getHorizontalLines()
        assertEquals(2, lines.size)
        assertEquals(ViewLine(startX = 0f, startY = 0f, endX = 100f, endY = 0f), lines[0])
        assertEquals(ViewLine(startX = 0f, startY = 100f, endX = 100f, endY = 100f), lines[1])
    }
    @Test fun `getHorizontalLines-withMargin`() {
        val tested = BoardToViewModel(100, 100, 1, 10)
        val lines = tested.getHorizontalLines()
        assertEquals(2, lines.size)
        assertEquals(ViewLine(startX = 10f, startY = 10f, endX = 90f, endY = 10f), lines[0])
        assertEquals(ViewLine(startX = 10f, startY = 90f, endX = 90f, endY = 90f), lines[1])
    }
    @Test fun `getHorizontalLines-2cells`() {
        val tested = BoardToViewModel(100, 100, 2, 0)
        val lines = tested.getHorizontalLines()
        assertEquals(3, lines.size)
        assertEquals(ViewLine(startX = 0f, startY = 0f, endX = 100f, endY = 0f), lines[0])
        assertEquals(ViewLine(startX = 0f, startY = 50f, endX = 100f, endY = 50f), lines[1])
        assertEquals(ViewLine(startX = 0f, startY = 100f, endX = 100f, endY = 100f), lines[2])
    }
    @Test fun `getHorizontalLines-2cells-WithMargin`() {
        val tested = BoardToViewModel(100, 100, 2, 10)
        val lines = tested.getHorizontalLines()
        assertEquals(3, lines.size)
        assertEquals(ViewLine(startX = 10f, startY = 10f, endX = 90f, endY = 10f), lines[0])
        assertEquals(ViewLine(startX = 10f, startY = 50f, endX = 90f, endY = 50f), lines[1])
        assertEquals(ViewLine(startX = 10f, startY = 90f, endX = 90f, endY = 90f), lines[2])
    }
    @Test fun `getVerticalLines`() {
        val tested = BoardToViewModel(100, 100, 1, 0)
        val lines = tested.getVerticalLines()
        assertEquals(2, lines.size)
        assertEquals(ViewLine(startX = 0f, startY = 0f, endX = 0f, endY = 100f), lines[0])
        assertEquals(ViewLine(startX = 100f, startY = 0f, endX = 100f, endY = 100f), lines[1])
    }
}