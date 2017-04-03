package mmo5.a5inarowmmo

import org.junit.Assert.assertEquals
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

    @Test fun `getHorizontalLines-NoSymmetric`() {
        val tested = BoardToViewModel(140, 100, 1, 0)
        val lines = tested.getHorizontalLines()
        assertEquals(2, lines.size)
        assertEquals(ViewLine(startX = 0f, startY = 20f, endX = 100f, endY = 20f), lines[0])
        assertEquals(ViewLine(startX = 0f, startY = 120f, endX = 100f, endY = 120f), lines[1])
    }
    @Test fun `getVerticalLines-NoSymmetric`() {
        val tested = BoardToViewModel(140, 100, 1, 0)
        val lines = tested.getVerticalLines()
        assertEquals(2, lines.size)
        assertEquals(ViewLine(startX = 0f, startY = 20f, endX = 0f, endY = 120f), lines[0])
        assertEquals(ViewLine(startX = 100f, startY = 20f, endX = 100f, endY = 120f), lines[1])
    }
    @Test fun `getMatrixLocationByXy`() {
        val tested = BoardToViewModel(100, 100, 1, 0)
        val location = tested.getMatrixLocationByXy(50f, 50f)
        assertEquals(Pair(0, 0), location)
    }
    @Test fun `getMatrixLocationByXy-2cells`() {
        val tested = BoardToViewModel(100, 100, 2, 0)
        val location = tested.getMatrixLocationByXy(70f, 70f)
        assertEquals(Pair(1, 1), location)
    }

    @Test fun `getRectFromIndex`() {
        val tested = BoardToViewModel(100, 100, 2, 0)
        val rect = tested.getRectFromIndex(0, 0)
        assertEquals(Rectangle(1, 1, 50, 50), rect)
    }
}