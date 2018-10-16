package frame.zzt.com.appframe.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import frame.zzt.com.appframe.R;

/**
 * 启动界面的进度条
 */


public class ProgressBarStart extends TextView {
    private static final String TAG = ProgressBarStart.class.getSimpleName();
    private Context mContext ;
    /**
     * 进度条最大值，默认为100
     */
    private int maxValue = 100;

    /**
     * 当前进度值
     */
    private int currentValue = 0;

    /**
     * 每次扫过的角度，用来设置进度条圆弧所对应的圆心角，alphaAngle=(currentValue/maxValue)*360
     */
    private float alphaAngle;

    /**
     * 底部圆弧的颜色，默认为Color.LTGRAY
     */
    private int firstColor;

    /**
     * 进度条圆弧块的颜色
     */
    private int secondColor;

    /**
     * 圆环的宽度
     */
    private int circleWidth;

    private int arcWidth;

    /**
     *
     * 圆的画笔
     */
    private Paint circlePaint;

    /**画圆弧的画笔*/
    private Paint arcPaint;

    /**
     * 画文字的画笔
     */
    private Paint textPaint;
    /**
     * 显示字体颜色
     */
    private int textColor;

    /**需要显示RSSi值*/
    private String showRssi ;
    /**设置显示状态*/
    private String showMsgState ;
//    private Paint textStatePaint;

    /**
     * 通过代码创建时才使用
     *
     * @param context
     */
    public ProgressBarStart(Context context)
    {
        this(context, null);
        this.mContext = context ;
    }

    /**
     * 当从xml中加载view的时候，这个构造器才会被调用。其第二个参数中就包含自定义的属性。
     *
     * @param context
     *            上下文
     * @param attrs
     *            自定义属性
     */
    public ProgressBarStart(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
        this.mContext = context ;
    }

    /**
     * 从xml加载时执行和应用一个特定的风格。这里有两种方式，一是从theme中获得，二是从style中获得。
     * 第三个参数官方有这样的说明： defStyle - The default style to apply to this view. If 0,
     * no style will be applied (beyond what is included in the theme). This may
     * either be an attribute resource, whose value will be retrieved from the
     * current theme, or an explicit style resource.
     * 默认的风格会被应用到这个view上。如果是0，没有风格将会被应用
     * （除了被包含在主题中）。这个也许是一个属性的资源，它的值是从当前的主题中检索，或者是一个明确的风格资源。
     *
     * @param context
     *            上下文
     * @param attrs
     *            自定义的属性
     * @param defStyleAttr
     *            自定义风格
     */
    public ProgressBarStart(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.mContext = context ;

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.circleProgressBar,
                defStyleAttr, 0);
        int n = ta.getIndexCount();

        for (int i = 0; i < n; i++)
        {
            int attr = ta.getIndex(i);
            switch (attr)
            {
                case R.styleable.circleProgressBar_firstColor:
                    firstColor = ta.getColor(attr, Color.LTGRAY); // 默认底色为亮灰色
                    break;
                case R.styleable.circleProgressBar_secondColor:
                    secondColor = ta.getColor(attr, Color.BLUE); // 默认进度条颜色为蓝色
                    break;
                case R.styleable.circleProgressBar_circleWidth:
                    arcWidth = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics())); // 默认圆弧宽度为6dp
                    break;
                default:
                    break;
            }
        }

        ta.recycle();

        // 设置字体默认值
        textColor =  getResources().getColor(R.color.text_rssi)  ; // 设置默认字体颜色

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true); // 抗锯齿
        arcPaint.setDither(true); // 防抖动
        arcPaint.setStrokeWidth(arcWidth);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true); // 抗锯齿
        circlePaint.setDither(true); // 防抖动
        circlePaint.setStrokeWidth(circleWidth);


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {// 分别获取期望的宽度和高度，并取其中较小的尺寸作为该控件的宽和高
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(measureWidth, measureHeight), Math.min(measureWidth, measureHeight));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int center = this.getWidth() / 2;
        int radiusCir = center - arcWidth / 2;
        int radiusArc = center - arcWidth / 2 - center / 3 ;

        Log.i(TAG , " 计算的圆半径：" +radiusCir+ " - 圆弧半径:" + radiusArc );
        drawCircle(canvas, center, radiusCir , radiusArc  ); // 绘制进度圆弧
        drawText(canvas, center, radiusArc);
    }

    /**
     * 绘制进度圆弧
     *
     * @param canvas
     *            画布对象
     * @param center
     *            圆心的x和y坐标
     * @param radius
     *            圆的半径
     */
    private void drawCircle(Canvas canvas, int center, int radiusCricle , int radius)
    {
        circlePaint.setColor(firstColor); // 设置底部圆环的颜色，这里使用第一种颜色
        circlePaint.setStyle(Paint.Style.STROKE); // 设置绘制的圆为空心
        canvas.drawCircle(center, center, radiusCricle, circlePaint); // 画底部的空心圆

        arcPaint.setColor(firstColor); // 设置底部圆环的颜色，这里使用第一种颜色
        arcPaint.setStyle(Paint.Style.STROKE); // 设置绘制的圆为空心
        arcPaint.setStrokeCap(Paint.Cap.ROUND); // 把每段圆弧改成圆角的

        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius); // 圆的外接正方形
        canvas.drawArc(oval, -240, 300, false, arcPaint);

        SweepGradient sweepGradient = new SweepGradient(center, center, new int[]{ ContextCompat.getColor(mContext ,R.color.color_start_gradient) ,
                ContextCompat.getColor(mContext ,R.color.color_end_gradient) }, null);
//        arcPaint.setShader(sweepGradient );
        arcPaint.setColor(secondColor); // 设置圆弧的颜色
        arcPaint.setStrokeCap(Paint.Cap.ROUND); // 把每段圆弧改成圆角的

        alphaAngle = currentValue * 300.0f / maxValue * 1.0f; // 计算每次画圆弧时扫过的角度，这里计算要注意分母要转为float类型，否则alphaAngle永远为0
        canvas.drawArc(oval, -240, alphaAngle, false, arcPaint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     *            画布对象
     * @param center
     *            圆心的x和y坐标
     * @param radius
     *            圆的半径
     */
    private void drawText(Canvas canvas, int center, int radius)
    {

        textPaint.setTextAlign(Paint.Align.CENTER); // 设置文字居中，文字的x坐标要注意
        textPaint.setColor(textColor); // 设置文字颜色
        textPaint.setTextSize(100); // 设置要绘制的文字大小
        textPaint.setStrokeWidth(0); // 注意此处一定要重新设置宽度为0,否则绘制的文字会重叠
        textPaint.setFakeBoldText(true);// 设置为粗体
        Rect bounds = new Rect(); // 文字边框
        textPaint.getTextBounds(showMsgState, 0, showMsgState.length(), bounds); // 获得绘制文字的边界矩形
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt(); // 获取绘制Text时的四条线
        int baseline = center + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom; // 计算文字的基线,方法见http://blog.csdn.net/harvic880925/article/details/50423762
        canvas.drawText(showMsgState , center, baseline, textPaint); // 绘制表示进度的文字


        textPaint.setTextAlign(Paint.Align.CENTER); // 设置文字居中，文字的x坐标要注意
        textPaint.setColor(textColor); // 设置文字颜色
        textPaint.setTextSize(40); // 设置要绘制的文字大小
        textPaint.setStrokeWidth(0); // 注意此处一定要重新设置宽度为0,否则绘制的文字会重叠
        textPaint.setFakeBoldText(false);// 设置不为粗体
        Rect boundsMsg = new Rect(); // 文字边框
        textPaint.getTextBounds( showRssi, 0, showRssi.length(), boundsMsg); // 获得绘制文字的边界矩形
        Paint.FontMetricsInt fontMetricsMsg = textPaint.getFontMetricsInt(); // 获取绘制Text时的四条线

        int y = baseline +  (fontMetricsMsg.bottom - fontMetricsMsg.top) * 2 ;


//        int y = baseline - (fontMetricsMsg.bottom - fontMetricsMsg.top) + (fontMetricsMsg.bottom - fontMetricsMsg.top) / 2 - fontMetricsMsg.bottom; // 计算文字的基线,方法见http://blog.csdn.net/harvic880925/article/details/50423762
        canvas.drawText(showRssi, center, y, textPaint); // 绘制表示进度的文字

    }

    /**
     * 设置圆环的宽度
     *
     * @param width
     */
    public void setArcWidth(int width)
    {
        this.arcWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources()
                .getDisplayMetrics());
        arcPaint.setStrokeWidth(arcWidth);
        invalidate();
    }

    public void setCircleWidth(int width)
    {
        this.circleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources()
                .getDisplayMetrics());
        circlePaint.setStrokeWidth(circleWidth);
        invalidate();
    }

    /**
     * 设置圆环的底色，默认为亮灰色LTGRAY
     *
     * @param color
     */
    public void setFirstColor(int color)
    {
        this.firstColor = color;
        arcPaint.setColor(firstColor);
        invalidate();
    }

    /**
     * 设置进度条的颜色，默认为蓝色<br>
     *
     * @param color
     */
    public void setSecondColor(int color)
    {
        this.secondColor = color;
        arcPaint.setColor(secondColor);
        invalidate();
    }

    public void setTextColor(int color)
    {
        this.textColor = color;
        textPaint.setColor(secondColor);
        invalidate();
    }



    /**
     * 获取当前进度
     * @return
     */
    public int getProgress(){
        return currentValue ;
    }

    /**
     * 按进度显示百分比
     *
     * @param progress
     *            进度，值通常为0到100
     */
    public void setProgress(int progress , int rssi) {
        this.showRssi = rssi + "db" ;
        int percent = progress * maxValue / 100;
        if (percent < 0)
        {
            percent = 0;
        }
        if (percent > 100)
        {
            percent = 100;
        }
        this.currentValue = percent;

        if (rssi < -90 ) {
            showMsgState = "弱" ;
            setSecondColor(getResources().getColor(R.color.rssi_weak ));
        }else if (rssi <= -75 && rssi >= -90  ){
            showMsgState = "中" ;
            setSecondColor(getResources().getColor(R.color.rssi_medium ));
        }else if (rssi > -75  ){
            showMsgState = "强" ;
            setSecondColor(getResources().getColor(R.color.rssi_strong ));
        }else {
            showMsgState = "弱" ;
            setSecondColor(getResources().getColor(R.color.rssi_weak ));
        }
        invalidate();
    }

    /**
     * 按进度显示百分比，可选择是否启用数字动画
     *
     * @param progress
     *            进度，值通常为0到100
     * @param useAnimation
     *            是否启用动画，true为启用
     */
    public void setProgress(int progress, boolean useAnimation)
    {
        int percent = progress * maxValue / 100;
        if (percent < 0)
        {
            percent = 0;
        }
        if (percent > 100)
        {
            percent = 100;
        }
        if (useAnimation) // 使用动画
        {
            ValueAnimator animator = ValueAnimator.ofInt(0, percent);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    currentValue = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(1000);
            animator.start();

        }
        else
        {
//            setProgress(progress);
        }
    }
}