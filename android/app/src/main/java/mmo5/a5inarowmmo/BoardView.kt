package mmo5.a5inarowmmo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

class BoardView(val mainActivity: MainActivity) : View(mainActivity) {

    private val paint: Paint = Paint()
    private val playersColors = listOf(BLUE, RED, CYAN, YELLOW)
    private var boardModel: BoardToViewModel = BoardToViewModel.NullObject
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.WHITE
        canvas.drawPaint(paint)
        paint.color = Color.BLACK
        if (boardModel == BoardToViewModel.NullObject) {
            boardModel = BoardToViewModel(canvas.height, canvas.width, 15, 50)
        }
        boardModel.getHorizontalLines().forEach {
            canvas.drawLine(it.startX, it.startY, it.endX, it.endY, paint)
        }
        boardModel.getVerticalLines().forEach {
            canvas.drawLine(it.startX, it.startY, it.endX, it.endY, paint)
        }
        boardModel.getRectangles().forEach {
            paint.color = it.color
            canvas.drawRect(it.rect.toRect(), paint)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        mainActivity.sendTouch(boardModel.getMatrixLocationByXy(x, y))
        return super.onTouchEvent(event)
    }

    fun setRectByIndex(playerMove: PlayerMove) {
        boardModel.setRectByIndex(Pair(playerMove.position.x, playerMove.position.y),
                playersColors[playerMove.playerId.rem(playersColors.size)])
        invalidate()
    }
}

private fun Rectangle.toRect(): Rect = Rect(this.leftX, this.topY, this.rightX, this.bottomY)
