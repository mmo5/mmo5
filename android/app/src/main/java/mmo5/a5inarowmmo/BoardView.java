package mmo5.a5inarowmmo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class BoardView extends View {

    private final Paint paint;

    public BoardView(Context context) {
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        paint.setColor(Color.BLACK);

        for (int i = 0; i < canvas.getHeight(); i += 40) {
            canvas.drawLine(i, 0, i, canvas.getHeight(), paint);
            canvas.drawLine(0, i, canvas.getWidth(), i, paint);
        }
    }
}
