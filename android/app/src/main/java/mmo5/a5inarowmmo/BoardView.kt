package mmo5.a5inarowmmo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class BoardView(context: Context) : View(context) {

    private val paint: Paint

    init {
        paint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.WHITE
        canvas.drawPaint(paint)
        paint.color = Color.BLACK

        for (i in 0 until canvas.height / 40) {
        val j = (i * 40).toFloat()
            canvas.drawLine(j, 0f, j, canvas.height.toFloat(), paint)
            canvas.drawLine(0f, j, canvas.width.toFloat(), j, paint)
        }
    }
}
