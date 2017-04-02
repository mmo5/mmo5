package mmo5.a5inarowmmo


class BoardToViewModel(height: Int, width: Int, val numOfCells: Int, val margin: Int) {

    private val sizeBeforeMarginReduction = Math.min(height, width)
    private val sizeAfterMarginReduction = sizeBeforeMarginReduction - (margin * 2)
    private val boxSize = (sizeAfterMarginReduction / numOfCells) - 2 // 2 is for lines
    fun getHorizontalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (margin + index * (boxSize + 2)).toFloat()
            ViewLine(
                startX = margin.toFloat(),
                startY = horizontalValue,
                endX = (sizeBeforeMarginReduction - margin).toFloat(),
                endY = horizontalValue
        ) }
    }

    fun  getVerticalLines(): List<ViewLine> {
        return (0..numOfCells).map { index ->
            val horizontalValue = (margin + index * (boxSize + 2)).toFloat()
            ViewLine(
                    startY = margin.toFloat(),
                    startX = horizontalValue,
                    endY = (sizeBeforeMarginReduction - margin).toFloat(),
                    endX = horizontalValue
            ) }
    }
    //getRectByXy
    //getHorizontalLines
    //getVerticalLines
}

data class ViewLine(val startX: Float, val startY: Float, val endX: Float, val endY: Float)