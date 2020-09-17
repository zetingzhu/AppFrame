package com.example.signatureview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;

public class SlideView extends View {

    private static final String TAG = SlideView.class.getSimpleName();

    // 画笔宽度
    public static final int PEN_WIDTH = 10;
    // 画笔颜色
    public static final int PEN_COLOR = Color.BLACK;
    // 画布颜色
    public static final int BACK_COLOR = Color.WHITE;
    //画笔x坐标起点
    private float mPenX;
    //画笔y坐标起点
    private float mPenY;
    private Paint mPaint = new Paint();
    // 当前画布路径
    private Path mPath = new Path();
    // 缓存画布路据
    private Path mCachePath = new Path();
    // 缓存画布
    private Canvas mCanvas;
    // 缓存的图片
    private Bitmap cacheBitmap;
    //画笔宽度
    private int mPentWidth = PEN_WIDTH;
    //画笔颜色
    private int mPenColor = PEN_COLOR;
    //画板颜色
    private int mBackColor = BACK_COLOR;
    private boolean isTouched = false;
    // 设置图片宽度
    private int imgWidth;
    // 设置画布宽度是视图宽度的倍数
    private float widthScale = 2.0f;
    // 移动x轴位移
    private int moveDX = 0;
    // 是否移动画布
    private boolean isMove = false;
    // Bitmap Config
    private Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_4444;

    public SlideView(Context context) {
        this(context, null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SignatureView);
        mPenColor = typedArray.getColor(R.styleable.SignatureView_penColor, PEN_COLOR);
        mBackColor = typedArray.getColor(R.styleable.SignatureView_backColor, BACK_COLOR);
        mPentWidth = typedArray.getInt(R.styleable.SignatureView_penWidth, PEN_WIDTH);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mPentWidth);
        mPaint.setColor(mPenColor);
    }

    public boolean getTouched() {
        return isTouched;
    }

    public Bitmap getBitmap() {
        return cacheBitmap;
    }


    public void setPentWidth(int pentWidth) {
        mPentWidth = pentWidth;
    }

    public void setPenColor(@ColorInt int penColor) {
        mPenColor = penColor;
    }

    public void setBackColor(@ColorInt int backColor) {
        mBackColor = backColor;
    }

    public int getBackColor() {
        return mBackColor;
    }


    /**
     * 清空签名
     */
    public void clear() {
        if (mCanvas != null) {
            isTouched = false;
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mCanvas.drawColor(mBackColor);
            invalidate();
        }
    }

    public void setWidthScale(float scale) {
        this.widthScale = scale;
    }

    /**
     * 加大一屏画布
     */
    public void addWidthScale() {
        if (widthScale >= 5) {
            Toast.makeText(getContext(), "请不要写错别字，你的名字太长了，请重写！！！", Toast.LENGTH_SHORT).show();
            return;
        }
        this.widthScale += 1;
        imgWidth = (int) (getWidth() * widthScale);
        Bitmap newBitmap = Bitmap.createBitmap(imgWidth, getHeight(), bitmapConfig);
        mCanvas = new Canvas(newBitmap);
        mCanvas.drawColor(mBackColor);
        mCanvas.drawBitmap(cacheBitmap, 0f, 0f, mPaint);
        cacheBitmap = newBitmap;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "绘制签名：onSizeChanged = w:" + w);
        imgWidth = (int) (w * widthScale);
        cacheBitmap = Bitmap.createBitmap(imgWidth, getHeight(), bitmapConfig);
        mCanvas = new Canvas(cacheBitmap);
        mCanvas.drawColor(mBackColor);
        isTouched = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "绘制签名：onDraw =isMove:" + isMove + " - moveDX:" + moveDX);
//        if (isMove) {
//            canvas.save();
//            canvas.translate(moveDX, 0);
//            canvas.restore();
//            canvas.drawBitmap(cacheBitmap, -moveDX, 0, mPaint);
//            isMove = false;
//        } else {
        canvas.drawBitmap(cacheBitmap, -moveDX, 0, mPaint);
        canvas.drawPath(mPath, mPaint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "绘制签名：onTouchEvent moveDX:" + moveDX);
                mPenX = event.getX();
                mPenY = event.getY();
                mPath.moveTo(mPenX, mPenY);
                mCachePath.moveTo(mPenX + moveDX, mPenY);
                return true;
            case MotionEvent.ACTION_MOVE:
                isTouched = true;
                float x = event.getX();
                float y = event.getY();
                float penX = mPenX;
                float penY = mPenY;
                float dx = Math.abs(x - penX);
                float dy = Math.abs(y - penY);
                if (dx >= 3 || dy >= 3) {
                    float cx = (x + penX) / 2;
                    float cy = (y + penY) / 2;
                    mPath.quadTo(penX, penY, cx, cy);
                    mPenX = x;
                    mPenY = y;
                    // 缓存画笔需要根据左右移动来调整位置
                    float ccx = (x + penX + moveDX * 2) / 2;
                    float ccy = (y + penY) / 2;
                    mCachePath.quadTo(penX + moveDX, penY, ccx, ccy);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mCachePath, mPaint);
                mPath.reset();
                mCachePath.reset();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 设置移动百分比
     */
    public void setWidthTranslate(double percent) {
        this.moveDX = (int) (getWidth() * (widthScale - 1) * percent);
        Log.d(TAG, "绘制签名：moveDX：" + moveDX + " = percent:" + percent);
        isMove = true;
        invalidate();
    }

}
