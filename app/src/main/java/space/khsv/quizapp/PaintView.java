package space.khsv.quizapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class PaintView extends View {
    private Paint paint;
    private List<Path> paths = new ArrayList<>();
    private List<Paint> paints = new ArrayList<>();
    private Path path;
    private Bitmap backgroundImage; // Obrázek jako pozadí

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);
        path = new Path();

        // Načtení obrázku z res/drawable
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.yoda);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Vykreslení obrázku jako pozadí
        if (backgroundImage != null) {
            canvas.drawBitmap(backgroundImage, 0, 0, null);
        }

        // Vykreslení všech předchozích cest
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), paints.get(i));
        }

        // Vykreslení aktuální cesty
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(x, y);
                paths.add(path);
                Paint newPaint = new Paint(paint);
                paints.add(newPaint);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();
        return true;
    }

    public void changeColor(int color) {
        paint.setColor(color);
    }

    public void clearCanvas() {
        paths.clear();
        paints.clear();
        invalidate();
    }
}