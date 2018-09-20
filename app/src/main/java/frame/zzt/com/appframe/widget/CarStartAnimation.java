package frame.zzt.com.appframe.widget;

/**
 * Created by allen on 17/10/10.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import frame.zzt.com.appframe.R;


/**
 * Created on 2016/7/12.
 *
 * @author Yan Zhenjie.
 */
public class CarStartAnimation extends TextView {

    /**
     * 外部轮廓的颜色。
     */
    private int outLineColor = Color.BLACK;
    /**
     * 底部圆弧的颜色，默认为Color.LTGRAY
     */
    private int firstColor = Color.LTGRAY;
    /**
     * 外部轮廓的宽度。
     */
    private int outLineWidth = 2;

    /**
     * 内部圆的颜色。
     */
    private ColorStateList inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
    /**
     * 中心圆的颜色。
     */
    private int circleColor;

    /**
     * 进度条的颜色。
     */
    private int progressLineColor = Color.YELLOW;

    /**
     * 进度条的宽度。
     */
    private int progressLineWidth = 8;

    /**
     * 画笔。
     */
    private Paint mPaint = new Paint();

    /**
     * 进度条的矩形区域。
     */
    private RectF mArcRect = new RectF();
    /**
     *  换底部圆环的画笔
     */
    private Paint mPaintFirst = new Paint();
    /**
     * 进度。
     */
    private int progress = 100;
    /**
     * 进度条类型。
     */
    private ProgressType mProgressType = ProgressType.COUNT_BACK;
    /**
     * 进度倒计时时间。
     */
    private long timeMillis = 1000;

    /**
     * View的显示区域。
     */
    final Rect bounds = new Rect();
    /**
     * 进度条通知。
     */
    private OnCountdownProgressListener mCountdownProgressListener;
    /**
     * Listener what。
     */
    private int listenerWhat = 0;
    /**
     *  设置渐变颜色
     */
    private int[] mGradientColors ;
    /**
     * 超时时间
     */
    private long timeOut = 0 ;
    private Handler mHandler ;
    private Runnable mRunnable ;
    private boolean isSetTimeout = false ;// 是否设置超时

    public CarStartAnimation(Context context) {
        this(context, null);
    }

    public CarStartAnimation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarStartAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CarStartAnimation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    /**
     * 初始化。
     *
     * @param context      上下文。
     * @param attributeSet 属性。
     */
    private void initialize(Context context, AttributeSet attributeSet) {
        mPaint.setAntiAlias(true);
        mPaintFirst.setAntiAlias(true); // 抗锯齿
        mPaintFirst.setDither(true); // 防抖动

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleTextProgressbar);
        if (typedArray.hasValue(R.styleable.CircleTextProgressbar_in_circle_color))
            inCircleColors = typedArray.getColorStateList(R.styleable.CircleTextProgressbar_in_circle_color);
        else
            inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
        circleColor = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        typedArray.recycle();

        mGradientColors = new int[]{ ContextCompat.getColor(context , R.color.color_start_gradient ),
                ContextCompat.getColor(context ,R.color.color_end_gradient ) };
        firstColor =  ContextCompat.getColor(context ,R.color.color_bg_gradient ) ;
    }

    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineColor 颜色值。
     */
    public void setOutLineColor(@ColorInt int outLineColor) {
        this.outLineColor = outLineColor;
        invalidate();
    }

    /**
     * 设置外部轮廓的颜色。
     *
     * @param outLineWidth 颜色值。
     */
    public void setOutLineWidth(@ColorInt int outLineWidth) {
        this.outLineWidth = outLineWidth;
        invalidate();
    }

    /**
     * 设置圆形的填充颜色。
     *
     * @param inCircleColor 颜色值。
     */
    public void setInCircleColor(@ColorInt int inCircleColor) {
        this.inCircleColors = ColorStateList.valueOf(inCircleColor);
        invalidate();
    }

    /**
     * 是否需要更新圆的颜色。
     */
    private void validateCircleColor() {
        int circleColorTemp = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        if (circleColor != circleColorTemp) {
            circleColor = circleColorTemp;
            invalidate();
        }
    }

    /**
     * 设置进度条颜色。
     *
     * @param progressLineColor 颜色值。
     */
    public void setProgressColor(@ColorInt int progressLineColor) {
        this.progressLineColor = progressLineColor;
        invalidate();
    }

    /**
     * 设置进度条线的宽度。
     *
     * @param progressLineWidth 宽度值。
     */
    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
        invalidate();
    }

    /**
     * 设置进度。
     *
     * @param progress 进度。
     */
    public void setProgress(int progress) {
        this.progress = validateProgress(progress);
        invalidate();
    }

    /**
     * 验证进度。
     *
     * @param progress 你要验证的进度值。
     * @return 返回真正的进度值。
     */
    private int validateProgress(int progress) {
        if (progress > 100)
            progress = 100;
        else if (progress < 0)
            progress = 0;
        return progress;
    }

    /**
     * 拿到此时的进度。
     *
     * @return 进度值，最大100，最小0。
     */
    public int getProgress() {
        return progress;
    }

    /**
     * 设置倒计时总时间。
     *
     * @param timeMillis 毫秒。
     */
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    /**
     * 拿到进度条计时时间。
     *
     * @return 毫秒。
     */
    public long getTimeMillis() {
        return this.timeMillis;
    }

    /**
     * 设置进度条类型。
     *
     * @param progressType {@link ProgressType}.
     */
    public void setProgressType(ProgressType progressType) {
        this.mProgressType = progressType;
        resetProgress();
        invalidate();
    }

    /**
     * 暂停进度条
     */
    public void stopProgress(CarStartAnimation.ProgressType progressType){
        this.mProgressType = progressType;
    }

    /**
     * 重置进度。
     */
    private void resetProgress() {
        switch (mProgressType) {
            case COUNT:
                progress = 0;
                break;
            case COUNT_BACK:
                progress = 100;
                break;
        }
    }

    /**
     * 拿到进度条类型。
     *
     * @return
     */
    public ProgressType getProgressType() {
        return mProgressType;
    }

    /**
     * 设置进度监听。
     *
     * @param mCountdownProgressListener 监听器。
     */
    public void setCountdownProgressListener(int what, OnCountdownProgressListener mCountdownProgressListener) {
        this.listenerWhat = what;
        this.mCountdownProgressListener = mCountdownProgressListener;
    }

    /**
     * 开始。
     */
    public void start() {
        stop();
        post(progressChangeTask);
    }

    /**
     * 重新开始。
     */
    public void reStart() {
        resetProgress();
        start();
    }

    /**
     * 是否设置超时
     * @param boo
     */
    public void isSetTimeout( boolean boo){
        isSetTimeout = boo ;
    }

    /**
     *  设置超时时间
     */
    public void setTimeout(long tim) {
        if (isSetTimeout) {
            this.timeOut = tim;
            mHandler = new Handler();
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    progress = 93;
                    invalidate();
                    postDelayed(progressChangeTask, timeMillis / 100);
                    mCountdownProgressListener.onProgressTimeout();
                }
            };
            mHandler.postDelayed(mRunnable, tim);
        }
    }

    /**
     * 停止。
     */
    public void stop() {
        removeCallbacks(progressChangeTask);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取view的边界
        getDrawingRect(bounds);

        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = size / 2;

        //画内部背景
        int circleColor = inCircleColors.getColorForState(getDrawableState(), 0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(circleColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth, mPaint);

        //画边框圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(outLineWidth);
        mPaint.setColor(outLineColor);
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth / 2, mPaint);

        //画字
        Paint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        float textY = bounds.centerY() - (paint.descent() + paint.ascent()) / 2;
        canvas.drawText(getText().toString(), bounds.centerX(), textY, paint);


        int deleteWidth = progressLineWidth + outLineWidth;
        mArcRect.set(bounds.left + deleteWidth / 2, bounds.top + deleteWidth / 2, bounds.right - deleteWidth / 2, bounds.bottom - deleteWidth / 2);
        // 画底部的圆环
        mPaintFirst.setStyle(Paint.Style.STROKE);
        mPaintFirst.setStrokeWidth(progressLineWidth);
        mPaintFirst.setStrokeCap(Paint.Cap.BUTT);//
        mPaintFirst.setColor(firstColor); // 设置底部圆环的颜色，这里使用第一种颜色
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - deleteWidth / 2, mPaintFirst);


        //画进度条
        mPaint.setColor(progressLineColor);
        SweepGradient mSweepGradient = new SweepGradient(bounds.centerX(), bounds.centerY(),
                mGradientColors,
                null);
        //旋转渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(-90f, bounds.centerX(), bounds.centerY());
        mSweepGradient.setLocalMatrix(matrix);
        mPaint.setShader(mSweepGradient);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(progressLineWidth);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawArc(mArcRect, -90, 360 * progress / 100, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int lineWidth = 2 * (outLineWidth + progressLineWidth);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = (width > height ? width : height) + lineWidth;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        validateCircleColor();
    }

    /**
     * 进度更新task。
     */
    private Runnable progressChangeTask = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(this);
            switch (mProgressType) {
                case COUNT:
                    progress += 1;
                    break;
                case COUNT_BACK:
                    progress -= 1;
                    break;
                case PROGRESS_STOP:
                    postDelayed(progressChangeTask, timeMillis / 100);
                    break ;
            }
            if (progress >= 0 && progress <= 100) {
                if (mCountdownProgressListener != null)
                    mCountdownProgressListener.onProgress(listenerWhat, progress);
                if(progress == 92){
                    // 当如果进度条到达了92 默认已经成功了
                    if (mHandler != null && mRunnable != null) {
                        mHandler.removeCallbacks(mRunnable);
                    }
                    mCountdownProgressListener.onProgressSucced(timeMillis * 8 /100);
                }
                invalidate();
                postDelayed(progressChangeTask, timeMillis / 100);
            } else
                progress = validateProgress(progress);
        }
    };

    /**
     * 进度条类型。
     */
    public enum ProgressType {
        /**
         * 顺数进度条，从0-100；
         */
        COUNT,

        /**
         * 倒数进度条，从100-0；
         */
        COUNT_BACK,
        /**
         * 进度条暂停
         */
        PROGRESS_STOP,

        /*
        * 进度条到达70%
        * */
        PROGRESS_70 ;
    }

    /**
     * 进度监听。
     */
    public interface OnCountdownProgressListener {

        /**
         * 进度通知。
         *
         * @param progress 进度值。
         */
        void onProgress(int what, int progress);

        /**
         *  当进度到达了95以上说明执行成功了，执行后面的操作，变换图片什么的
         * @param time 剩余时间，用来执行后面操作动画时间
         */
        void onProgressSucced(long time);

        /**
         * 当请求超时的时候动画结束
         */
        void onProgressTimeout();

    }
}