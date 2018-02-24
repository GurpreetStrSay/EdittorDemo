package com.example.instasite.edittordemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class DrawingView extends View {

    // To hold the path that will be drawn.
    private Path drawPath;
    private boolean movetext = true;
    // Paint object to draw drawPath and drawCanvas.
    private Paint drawPaint, canvasPaint;
    // initial color
    private int paintColor = 0xffffffff;
    private int previousColor = paintColor;
    // canvas on which drawing takes place.
    private Canvas drawCanvas;
    // canvas bitmap
    private Bitmap canvasBitmap;
    Canvas canvas1;
    float touchSX = 0;
    float touchSY = 0;
    float touchEX = 0;
    float touchEY = 0;
    ArrayList<Float> AtouchSX = new ArrayList<>();
    ArrayList<Float> AtouchSY = new ArrayList<>();
    ArrayList<Float> AtouchEX = new ArrayList<>();
    ArrayList<Float> AtouchEY = new ArrayList<>();
    // Brush stroke width
    private float brushSize, lastBrushSize;
    // To enable and disable erasing mode.
    private boolean erase = false, remove = false;
    public static ArrayList<Path> paths = new ArrayList<>();

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpDrawing();
    }

    public DrawingView(Context mainActivity) {
        super(mainActivity);
    }


    private void setUpDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        // Making drawing smooth.
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);

        // Initial brush size is medium.
        drawPaint.setStrokeWidth(6);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(1, 1, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setUpDrawing();

            for (int i = 0; i < AtouchEX.size(); i++) {
//            canvas.drawLine(AtouchSX.get(i), AtouchSY.get(i), AtouchEX.get(i), AtouchEY.get(i), drawPaint);

                if (AtouchSX.get(i) > 0 && AtouchSY.get(i) > 0 && AtouchEX.get(i) > 0 && AtouchEY.get(i) > 0) {

                    canvas.drawLine(AtouchSX.get(i), AtouchSY.get(i), AtouchEX.get(i), AtouchEY.get(i), drawPaint);
                    float delta_x = AtouchEX.get(i) - AtouchSX.get(i);
                    float delta_y = AtouchEY.get(i) - AtouchSY.get(i);
                    float ANgle = (float) (Math.atan2(delta_y, delta_x) * 57.28);
                    ANgle = (ANgle + 360) % 360;
                    float x2 = (AtouchEX.get(i) + AtouchSX.get(i)) / 2;
                    float y2 = (AtouchEY.get(i) + AtouchSY.get(i)) / 2;
                    x2 = (AtouchEX.get(i) + x2) / 2;
                    y2 = (AtouchEY.get(i) + y2) / 2;
                    x2 = (AtouchEX.get(i) + x2) / 2;
                    y2 = (AtouchEY.get(i) + y2) / 2;
                    if (ANgle >= 315 && ANgle <= 359) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2, y2 + 20, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2, y2 - 20, drawPaint);
                    } else if (ANgle >= 270 && ANgle <= 314) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 20, y2 + 15, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 - 25, y2 - 10, drawPaint);
                    } else if (ANgle >= 225 && ANgle <= 269) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 30, y2, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 - 35, y2, drawPaint);
                    } else if (ANgle >= 180 && ANgle <= 224) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 10, y2 - 10, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 - 10, y2 + 20, drawPaint);
                    } else if (ANgle >= 135 && ANgle <= 179) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 10, y2 - 30, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 10, y2 + 30, drawPaint);
                    } else if (ANgle >= 90 && ANgle <= 134) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 - 20, y2 - 20, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 20, y2 + 10, drawPaint);

                    } else if (ANgle >= 45 && ANgle <= 89) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 - 30, y2, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 25, y2, drawPaint);
                    } else if (ANgle >= 0 && ANgle <= 44) {
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2, y2 + 20, drawPaint);
                        canvas.drawLine(AtouchEX.get(i), AtouchEY.get(i), x2 + 20, y2 - 10, drawPaint);
                    }

                    canvas.save();
                }
            }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (movetext) {
                    touchSY = 0;
                    touchSX = 0;
                    touchSX = event.getX();
                    touchSY = event.getY();
                    if(AtouchSX.size()<=0){

                        AtouchSX.add(touchSX);
                        AtouchSY.add(touchSY);
                    }else {
                        movetext=false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (movetext) {
                    touchEX = event.getX();
                    touchEY = event.getY();
                    if(AtouchEX.size()<=0){
                        AtouchEX.add(touchEX);
                        AtouchEY.add(touchEY);
                    }else {
                        movetext=false;
                    }

                }
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setColor(String newColor) {
        // invalidate the view
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
        previousColor = paintColor;
    }


    public void startNew() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void removePath() {
        remove = true;
        int posti = AtouchEX.size() - 1;
        if (posti != -1) {
            AtouchEX.remove(posti);
            AtouchEY.remove(posti);
            AtouchSX.remove(posti);
            AtouchSY.remove(posti);
            invalidate();
        }
    }

    public void moveArrow() {
        movetext = false;
    }
}
