package mmo5.a5inarowmmo


class BoardToViewModel(val height: Int, val width: Int, val numOfCells: Int, margin: Int) {

    private val isXBigger = height < width
    private val marginX = if (!isXBigger) margin else Math.abs(height - width) / 2 + margin
    private val marginY = if (isXBigger) margin else Math.abs(height - width) / 2 + margin
    private val sizeBeforeMarginReduction = Math.min(height, width)
    private val sizeAfterMarginReduction = sizeBeforeMarginReduction - (margin * 2)
    private val boxSize = (sizeAfterMarginReduction / numOfCells) - 2 // 2 is for lines
    fun getHorizontalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (marginY + index * (boxSize + 2)).toFloat()
            ViewLine(
                startX = marginX.toFloat(),
                startY = horizontalValue,
                endX = (width - marginX).toFloat(),
                endY = horizontalValue
        ) }
    }

    fun  getVerticalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (marginX + index * (boxSize + 2)).toFloat()
            ViewLine(
                    startY = marginY.toFloat(),
                    startX = horizontalValue,
                    endY = (height - marginY).toFloat(),
                    endX = horizontalValue
            ) }
    }
    //getRectByXy
    //getHorizontalLines
    //getVerticalLines
}

data class ViewLine(val startX: Float, val startY: Float, val endX: Float, val endY: Float)