package com.example.signatureview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2021/1/6
 * 利用二维贝塞尔曲线平滑绘制
 * <p>
 * 如何平滑拖动鼠标绘制的点，如果有（A,B,C）三个点，如何平滑的画出曲线，可以取A,B的中点为起始点，B，C的中点为结束点，B点作为控制点，就可以绘制出和ABC弯曲度方向一样的曲线。
 */
public class ViewDemo17 extends View {

    private Paint paint;
    private Path mPath;
    private float lastX;
    private float lastY;

    public ViewDemo17(Context context) {
        this(context, null);
    }

    public ViewDemo17(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(15);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mPath.reset();
                lastX = event.getX();
                lastY = event.getY();
                mPath.moveTo(event.getX(), event.getY());
                return true;
            }
            case MotionEvent.ACTION_MOVE:
                float endx = (event.getX() + lastX) / 2;
                float endY = (event.getY() + lastY) / 2;
                mPath.quadTo(lastX, lastY, endx, endY);
                lastY = event.getY();
                lastX = event.getX();
                postInvalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}

