package mmo5.a5inarowmmo

import android.animation.Animator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth



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

        val matrixLocationByXy = boardModel.getMatrixLocationByXy(x, y)
        if (matrixLocationByXy.first < 0 || matrixLocationByXy.first >= boardModel.numOfCells ||
                matrixLocationByXy.second < 0 || matrixLocationByXy.second >= boardModel.numOfCells) {
            return true
        }
        showTouchEffect(x, y, matrixLocationByXy)
        mainActivity.sendTouch(matrixLocationByXy)
        return false
    }

    private fun showTouchEffect(x: Float, y: Float, matrixLocationByXy: Pair<Int, Int>) {
//        val finalRadius = 30f
//        val anim = ViewAnimationUtils.createCircularReveal(this, x.toInt(), y.toInt(), 0f, finalRadius)
//        this.visibility = View.VISIBLE
//        anim.start()
    }

    fun setRectByIndex(playerMove: PlayerMove) {
        boardModel.setRectByIndex(Pair(playerMove.position.x, playerMove.position.y),
                playersColors[playerMove.playerId.rem(playersColors.size)])
        invalidate()
    }

    fun announceWinner(winner: Winner) {
//        winner.positions.forEach {
//            boardModel.setRectByIndex(Pair(it.x, it.y), Color.GREEN)
//        }
        boardModel.resetMoves()
        invalidate()
    }
}

private fun Rectangle.toRect(): Rect = Rect(this.leftX, this.topY, this.rightX, this.bottomY)
