package mmo5.a5inarowmmo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import java.util.*

class BoardView(context: Context) : View(context) {

    private val paint: Paint = Paint()
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
        boardModel.setRectByXy(x, y, Random().nextInt())
        invalidate()
        return super.onTouchEvent(event)
    }
}

private fun  Rectangle.toRect(): Rect = Rect(this.leftX, this.topY, this.rightX, this.bottomY)
