package mmo5.a5inarowmmo


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
                (1..numOfCells).map { RectangleHolder(rect = Rectangle(0, 0, 0, 0), color = 0xFFFFFFFF.toInt() /**WHITE**/) }
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
        setRectByIndex(cellIndex, color)
    }

    fun setRectByIndex(cellIndex: Pair<Int, Int>, color: Int) {
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

    fun getRectFromIndex(x: Int, y: Int): Rectangle = Rectangle(
            leftX = x * boxSizeIncludeTopLeftLines + marginX,
            topY = y * boxSizeIncludeTopLeftLines + marginY,
            rightX = x * boxSizeIncludeTopLeftLines + marginX + boxSizeIncludeTopLeftLines,
            bottomY = y * boxSizeIncludeTopLeftLines + marginY + boxSizeIncludeTopLeftLines)

    object NullObject: BoardToViewModel(height = 1, width = 1, numOfCells = 1, margin = 0)
}

data class Rectangle(val leftX: Int, val topY: Int, val rightX: Int, val bottomY: Int)
data class RectangleHolder(val rect: Rectangle, val color: Int)
data class ViewLine(val startX: Float, val startY: Float, val endX: Float, val endY: Float)