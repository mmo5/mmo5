package mmo5.a5inarowmmo

import android.graphics.Color
import android.graphics.Rect


class BoardToViewModel(val height: Int, val width: Int, val numOfCells: Int, margin: Int) {

    private val isXBigger = height < width
    private val marginX = if (!isXBigger) margin else Math.abs(height - width) / 2 + margin
    private val marginY = if (isXBigger) margin else Math.abs(height - width) / 2 + margin
    private val sizeBeforeMarginReduction = Math.min(height, width)
    private val sizeAfterMarginReduction = sizeBeforeMarginReduction - (margin * 2)
    private val boxSize = (sizeAfterMarginReduction / numOfCells) - 2 // 2 is for lines
    private val rectangles: MutableList<MutableList<RectangleHolder>> =
            (1..numOfCells).map {
                (1..numOfCells).map { RectangleHolder(rect = Rect(0, 0, 0, 0), color = Color.WHITE) }
                        .toMutableList()
            }.toMutableList()

    fun getHorizontalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (marginY + index * (boxSize + 2)).toFloat()
            ViewLine(
                    startX = marginX.toFloat(),
                    startY = horizontalValue,
                    endX = (width - marginX).toFloat(),
                    endY = horizontalValue
            )
        }
    }

    fun getVerticalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (marginX + index * (boxSize + 2)).toFloat()
            ViewLine(
                    startY = marginY.toFloat(),
                    startX = horizontalValue,
                    endY = (height - marginY).toFloat(),
                    endX = horizontalValue
            )
        }
    }

    fun getRectangles(): List<RectangleHolder> {
        return rectangles.flatten()
    }

    fun setRectByXy(x: Float, y: Float, color: Int): Unit {
        val cellIndex = getMatrixLocationByXy(x, y)
        rectangles[cellIndex.first][cellIndex.second] = RectangleHolder(rect = getRectFromIndex(cellIndex.first, cellIndex.second), color = color)
    }

    fun getMatrixLocationByXy(x: Float, y: Float): Pair<Int, Int> {
        val resX = (x - marginX) / (boxSize + 1)
        val resY = (y - marginY) / (boxSize + 1)
        return Pair(resX.toInt(), resY.toInt())
    }

    fun getRectFromIndex(x: Int, y: Int): Rect {
        return Rect(x * boxSize + marginX + 1, y * boxSize + marginY + 1, x * boxSize + marginX + 1 + boxSize, y * boxSize + marginY + 1 + boxSize)
    }
}

data class RectangleHolder(val rect: Rect, val color: Int)
data class ViewLine(val startX: Float, val startY: Float, val endX: Float, val endY: Float)