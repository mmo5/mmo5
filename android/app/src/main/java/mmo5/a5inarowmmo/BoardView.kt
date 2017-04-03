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
        boardModel.getHorizontalLines().forEach { line ->
            canvas.drawLine(line.startX, line.startY, line.endX, line.endY, paint)
        }
        boardModel.getVerticalLines().forEach { line ->
            canvas.drawLine(line.startX, line.startY, line.endX, line.endY, paint)
        }
        boardModel.getRectangles().forEach {r ->
            paint.color = r.color
            canvas.drawRect(r.rect, paint)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.getX()
        val y = event.getY()
        boardModel.setRectByXy(x, y, Random().nextInt())
        invalidate();
        return super.onTouchEvent(event)
    }
}
