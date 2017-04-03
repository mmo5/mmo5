package mmo5.a5inarowmmo

import android.graphics.Color
import android.graphics.Rect


open class BoardToViewModel(height: Int, width: Int, val numOfCells: Int, margin: Int) {
    init {
        require(numOfCells > 0)
        require(margin >= 0)
        require(width >= numOfCells)
        require(height >= numOfCells)
    }
    private val roundedHeight = height - height.rem(numOfCells)
    private val roundedWidth = width - width.rem(numOfCells)
    private val isXBigger = roundedHeight < roundedWidth
    private val marginX = if (!isXBigger) margin else Math.abs(roundedHeight - roundedWidth) / 2 + margin
    private val marginY = if (isXBigger) margin else Math.abs(roundedHeight - roundedWidth) / 2 + margin
    private val sizeBeforeMarginReduction = Math.min(roundedHeight, roundedWidth)
    private val sizeAfterMarginReduction = sizeBeforeMarginReduction - (margin * 2)
    private val boxSizeIncludeTopLeftLines = (sizeAfterMarginReduction / numOfCells)
    private val rectangles: MutableList<MutableList<RectangleHolder>> =
            (1..numOfCells).map {
                (1..numOfCells).map { RectangleHolder(rect = Rect(0, 0, 0, 0), color = Color.WHITE) }
                        .toMutableList()
            }.toMutableList()

    fun getHorizontalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (marginY + index * (boxSizeIncludeTopLeftLines)).toFloat()
            ViewLine(
                    startX = marginX.toFloat(),
                    startY = horizontalValue,
                    endX = (marginX + numOfCells * (boxSizeIncludeTopLeftLines)).toFloat(),
                    endY = horizontalValue
            )
        }
    }

    fun getVerticalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (marginX + index * (boxSizeIncludeTopLeftLines)).toFloat()
            ViewLine(
                    startY = marginY.toFloat(),
                    startX = horizontalValue,
                    endY = (marginY + numOfCells * (boxSizeIncludeTopLeftLines)).toFloat(),
                    endX = horizontalValue
            )
        }
    }

    fun getRectangles(): List<RectangleHolder> {
        return rectangles.flatten()
    }

    fun setRectByXy(x: Float, y: Float, color: Int): Unit {
        val cellIndex = getMatrixLocationByXy(x, y)
        if (cellIndex.first < 0 || cellIndex.first >= numOfCells ||
                cellIndex.second < 0 || cellIndex.second >= numOfCells) {
            return
        }
        rectangles[cellIndex.first][cellIndex.second] = RectangleHolder(rect = getRectFromIndex(cellIndex.first, cellIndex.second), color = color)
    }

    fun getMatrixLocationByXy(x: Float, y: Float): Pair<Int, Int> {
        val resX = (x - marginX) / (boxSizeIncludeTopLeftLines)
        val resY = (y - marginY) / (boxSizeIncludeTopLeftLines)
        return Pair(resX.toInt(), resY.toInt())
    }

    fun getRectFromIndex(x: Int, y: Int): Rect = Rect(
            x * boxSizeIncludeTopLeftLines + marginX,
            y * boxSizeIncludeTopLeftLines + marginY,
            x * boxSizeIncludeTopLeftLines + marginX + boxSizeIncludeTopLeftLines,
            y * boxSizeIncludeTopLeftLines + marginY + boxSizeIncludeTopLeftLines)

    object NullObject: BoardToViewModel(height = 0, width = 0, numOfCells = 1, margin = 0)
}

data class RectangleHolder(val rect: Rect, val color: Int)
data class ViewLine(val startX: Float, val startY: Float, val endX: Float, val endY: Float)