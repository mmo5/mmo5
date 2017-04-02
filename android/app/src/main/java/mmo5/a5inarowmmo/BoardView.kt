package mmo5.a5inarowmmo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

class BoardView(context: Context) : View(context) {

    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.WHITE
        canvas.drawPaint(paint)
        paint.color = Color.BLACK
        val boardModel: BoardToViewModel = BoardToViewModel(canvas.height, canvas.width, 5, 50)
        boardModel.getHorizontalLines().forEach { line ->
            canvas.drawLine(line.startX, line.startY, line.endX, line.endY, paint)
        }
        boardModel.getVerticalLines().forEach { line ->
            canvas.drawLine(line.startX, line.startY, line.endX, line.endY, paint)
        }
//        val r = Rect()
//        canvas.drawRect(r, paint)
//        val boxSize = 80
//        for (i in 0 until canvas.height / boxSize) {
//        val j = (i * boxSize).toFloat()
//            canvas.drawLine(j, 0f, j, canvas.height.toFloat(), paint)
//            canvas.drawLine(0f, j, canvas.width.toFloat(), j, paint)
//        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var touched_x = event.getX()
        var touched_y = event.getY()
        return super.onTouchEvent(event)
    }
}
