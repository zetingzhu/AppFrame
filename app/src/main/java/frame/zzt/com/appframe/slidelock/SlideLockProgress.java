package frame.zzt.com.appframe.slidelock;

/**
 * Created by allen on 17/10/10.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import frame.zzt.com.appframe.R;


/**
 * Created on 2016/7/12.
 * <p>
 * 备份一下 CarStartAnimation 类的3.7完成的功能
 *
 * @author Yan Zhenjie.
 */
public class SlideLockProgress extends TextView {

    private static final String TAG = "VehiclePresenter";
    private Context mContext;
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
     * 覆盖层
     */
    private Paint mPaintMask = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 进度条的矩形区域。
     */
    private RectF mArcRect = new RectF();
    private RectF mRectYY = new RectF();

    /**
     * 进度。
     */
    private int progress = 50;
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
     * 换底部圆环的画笔
     */
    private Paint mPaintBottom;
    /**
     * 进度画笔。
     */
    private Paint mPaintProgress;
    /**
     * 设置文字
     */
    private String mText;
    ;
    /**
     * 文字画笔
     */
    private Paint mPaintText;
    /**
     * 文字大小
     */
    private Rect mRectText;

    /**
     * 超时时间
     */
    private long timeOut = 0;
    private Handler mHandler; // 蓝牙超时
    private Runnable mRunnable;// 蓝牙超时线程
    private Runnable mRunnableRead;// 蓝牙超时读取数据的线程
    private boolean isSetTimeout = false;// 是否设置超时

    // 是否停止
    private boolean ifStop = false;

    // 设置是否高亮显示
    private boolean isgaoliang = false;

    // 是否已经成功
    private boolean isSucced = false;
    // 是否超时, 停止了继续执行完动画
    private boolean isTimeout = false;
    // 设置执行步数，值越大，执行时间越短 ，最大不要超过 8
    private int isStep = 2;


    public SlideLockProgress(Context context) {
        this(context, null);
    }

    public SlideLockProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLockProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlideLockProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        this.mContext = context;


        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleTextProgressbar);
        if (typedArray.hasValue(R.styleable.CircleTextProgressbar_in_circle_color))
            inCircleColors = typedArray.getColorStateList(R.styleable.CircleTextProgressbar_in_circle_color);
        else
            inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
        circleColor = inCircleColors.getColorForState(getDrawableState(), Color.TRANSPARENT);
        typedArray.recycle();

        firstColor = ContextCompat.getColor(context, R.color.color_bg_gradient);
        progressLineColor = ContextCompat.getColor(context, R.color.color_end_gradient);

        // 设置底部圆画笔
        mPaintBottom = new Paint();
        mPaintBottom.setAntiAlias(true); // 抗锯齿
        mPaintBottom.setDither(true); // 防抖动
        mPaintBottom.setStyle(Paint.Style.STROKE);
        mPaintBottom.setStrokeWidth(progressLineWidth);
        mPaintBottom.setStrokeCap(Paint.Cap.BUTT);//
        mPaintBottom.setColor(firstColor); // 设置底部圆环的颜色，这里使用第一种颜色

        // 进度画笔
        mPaintProgress = new Paint();
        // 设置画笔是否抗锯齿
        mPaintProgress.setAntiAlias(true);
        // 描边
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setStrokeWidth(progressLineWidth);
        // 设置线帽样式，取值有Cap.ROUND(圆形线帽)、Cap.SQUARE(方形线帽)、Paint.Cap.BUTT(无线帽)
        mPaintProgress.setStrokeCap(Paint.Cap.BUTT);
        mPaintProgress.setColor(progressLineColor);

        // 获取文字
        mText = getText().toString();

        // 文字画笔
        mPaintText = new Paint();
        mPaintText.setStyle(Paint.Style.FILL);
        // 设置画笔是否抗锯齿
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(getTextSize());
        mPaintText.setColor(getCurrentTextColor());
        // 文字水平居中
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mRectText = new Rect(); // 文字边框
        mPaintText.getTextBounds(mText, 0, mText.length(), mRectText); // 获得绘制文字的边界矩形

        Log.e(TAG, "--" + mText);
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
     * 设置底部圆环值
     *
     * @param firstColor
     */
    public void setProgressBgColor(@ColorInt int firstColor) {
        this.firstColor = firstColor;
        invalidate();
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
    public void stopProgress(SlideLockProgress.ProgressType progressType) {
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
        ifStop = false;
        isSucced = false;
        isTimeout = false;
        post(progressChangeTask);
        setTimeoutStart();
    }

    /**
     * 重新开始。
     */
    public void reStart() {
        resetProgress();
        start();
    }

    /**
     * 设置超时后还可以继续执行
     *
     * @param boo
     */
    public void setTimeoutCount(boolean boo, int step) {
        this.isTimeout = boo;
        this.isStep = step;
        invalidate();
    }

    /**
     * 是否设置超时
     *
     * @param boo
     */
    public void isSetTimeout(boolean boo) {
        isSetTimeout = boo;
    }

    /**
     * 设置超时时间
     */
    public void setTimeout(long tim) {
        this.timeOut = tim;
    }

    /**
     * 设置超时时间
     */
    private void setTimeoutStart() {
        if (isSetTimeout) {
            Log.d(TAG, "设置超时时间");
            isStep = 2;
            mHandler = new Handler();
            mRunnable = new Runnable() {
                @Override
                public void run() {
//                    progress = 93;
//                    invalidate();
//                    postDelayed(progressChangeTask, timeMillis / 100);
//                    isTimeout = true ;
                    mCountdownProgressListener.onProgressTimeout();
                    setTimeoutCount(true, 2);
                    Log.d(TAG, " 已经超时了");
                }
            };
            mHandler.postDelayed(mRunnable, timeOut);
        }
    }

    private void removeTimeOut() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            mHandler = null;
            mRunnable = null;
        }
    }

    /**
     * 按钮打开状态
     */
    public void setShowOpen() {
        this.progress = validateProgress(progress);
        invalidate();
    }

    /**
     * 按钮关闭状态
     */
    public void setShowClose() {
        this.progress = validateProgress(progress);
        invalidate();
    }

    /**
     * 停止。
     */
    public void stop() {
        ifStop = true;
        removeCallbacks(progressChangeTask);
        removeTimeOut();
    }


    public void setGaoLiang(boolean gl) {
        this.isgaoliang = gl;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //计算基线
        Paint.FontMetricsInt fontMetricsInt = mPaintText.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        float baseLine = getHeight() * 0.5f + dy;
        float x = getWidth() * 0.5f;
        // x: 开始的位置  y：基线
        canvas.drawText(mText, x, baseLine, mPaintText);

        //获取view的边界
        getDrawingRect(bounds);

        //关闭硬件加速才能显示阴影
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
        float outerRadius = size / 2;
        // 圆环距离外部的边缘距离
        int deleteWidth = progressLineWidth + outLineWidth;
        mArcRect.set(bounds.left + deleteWidth / 2, bounds.top + deleteWidth / 2, bounds.right - deleteWidth / 2, bounds.bottom - deleteWidth / 2);
        // 画底部的圆环
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - deleteWidth / 2, mPaintBottom);

        //画进度条
        canvas.drawArc(mArcRect, -90, 360 * progress / 100, false, mPaintProgress);

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
            if (ifStop) {
                return;
            }
            switch (mProgressType) {
                case COUNT:
                    progress += 1 * isStep;
                    break;
                case COUNT_BACK:
                    progress -= 1 * isStep;
                    break;
                case PROGRESS_STOP:
                    postDelayed(progressChangeTask, timeMillis / 100);
                    break;
            }
//            Log.w(TAG , " -----progress: " + progress ) ;
            if (progress >= 0 && progress <= 100) {
                if (mCountdownProgressListener != null)
                    mCountdownProgressListener.onProgress(listenerWhat, progress);
                if (progress >= 92 && !isTimeout) {
                    // 当如果进度条到达了92 默认已经成功了
                    if (!isSucced) {
                        removeTimeOut();
                        mCountdownProgressListener.onProgressSucced(timeMillis * 8 / 100);
                        isSucced = true;
                    }
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
        PROGRESS_70;
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
         * 当进度到达了95以上说明执行成功了，执行后面的操作，变换图片什么的
         *
         * @param time 剩余时间，用来执行后面操作动画时间
         */
        void onProgressSucced(long time);

        /**
         * 当请求超时的时候动画结束
         */
        void onProgressTimeout();

    }
}