package frame.zzt.com.appframe.slidelock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.core.content.ContextCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import frame.zzt.com.appframe.R;

/**
 * Created by zeting
 * Date 18/12/11.
 */

public class SlideLockVerticalView extends ViewGroup {
    private static final String TAG = "VehicleNewFragment/Slv" ;

    private ViewDragHelper viewDragHelper;

    private Paint mPaint;// 绘制背景的
    private Paint mPaintCircle;// 绘制虚线圆
    private Paint mPaintLine ;// 绘制虚线
    private Paint mPaintMask ;// 绘制mask
    private Paint mPaintText;// 绘制 字体

    /**
     * 解锁触发阀值
     * 就是说当用户滚动滑块占当前视图宽度的百分比
     * 比如说 当前 视图宽度 = 1000px
     * 触发比率 = 0.75
     * 那么 触发阀值就是 750 = 1000 * 0.75
     */
    private float unlockTriggerValue = 0.99f;

    /**  动画时长 */
    private int animationTimeDuration = 300;


    /**  回弹动画实现 */
    private ObjectAnimator oa;

    /** 锁视图  */
    private View mLockView;

    /**
     * 圆角背景矩形
     */
    private RectF mRoundRect = new RectF();
    // 上下半部分圆弧
    private RectF mRoundArcTop = new RectF();
    private RectF mRoundArcBottom = new RectF();
    // 发光区域
    private Rect mRectW = new Rect();

    /**上下文对象*/
    private Context mContext ;

    /** 触摸事件时间 */
    private long downTime = 0L ;
    private long upTime = 0L ;
    private long leadTime = 0L ;
    /**是否显示滑动操作提醒*/
    private boolean isShowBg = false ;
    /** 是否可以滑动*/
    private boolean isMove = false ;
    /** 监听事件*/
    private OnTouchBring mTouchBring ;
    private SlideOnClickListener mClickListener ;
    /** 滑动的中心位置 */
    private float moveHeight = 0f ;
    /** 是否属于点击事件 */
    private boolean isOnClick = false ;

    /**是否正在执行 这个状态没用了 */
    private boolean isRun = false ;
    // 动画
    private CarStartAnimation3 progressbar; // 蓝牙车辆启动动画
    private ImageView startStatusBg; // 蓝牙车辆启动动画
    private TextView startStatusText; // 蓝牙车辆启动动画

    private long blueTimeout = 10 * 1000 ;// 执行蓝牙超时时间

    private boolean drawBg = false ;

    public SlideLockVerticalView(Context context) {
        this(context, null);
    }

    public SlideLockVerticalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLockVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context ;
        initView();
    }

    public float getMoveHeight() {
        return moveHeight;
    }

    public void setMoveHeight(float moveHeight) {
        this.moveHeight = moveHeight;

    }

    public boolean isRun() {
        return isRun;
    }

    /** 设置是否正在执行动画 */
    public void setRun(boolean run) {
        isRun = run;
//        Log.d(TAG , "--- isRun:" + isRun ) ;
    }

    /**设置是否可以滑动*/
    public void setIsMove( boolean move ){
        this.isMove = move ;
    }
    /**
     *  设置接触监听
     * @param mTouchBring
     */
    public void setOnTouchBring( OnTouchBring mTouchBring){
        this.mTouchBring = mTouchBring ;
    }

    /**
     * 设置点击监听
     * @param mSlideClick
     */
    public void setOnClickListener( SlideOnClickListener mSlideClick ){
        this.mClickListener = mSlideClick ;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        progressbar = (CarStartAnimation3) findViewById(R.id.custom_car_start_status);
        startStatusBg = (ImageView) findViewById(R.id.iv_car_start_status_bg);
        startStatusText = (TextView) findViewById(R.id.tv_car_start_status);
        Log.d(TAG , "---动画查找id") ;
    }

    private void initView() {
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setAlpha(20);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.i(TAG , "-----tryCaptureView 开始 ----- child == mLockView:" + (child == mLockView) );
//                int minX = 0;
//                int maxX = getWidth() - mLockView.getWidth();
//                return isEnabled() && (child.getLeft() > minX || child.getRight() < maxX) && child == mLockView;

                // 当 child 是 mHomeView 时，才允许捕获。
                downTime = System.currentTimeMillis() ;
                if (isMove && !isRun) {
                    isShowBg = true;
                }else {
                    isShowBg = false;
                }
                moveHeight = 0f ;
                postInvalidate();

                return child == mLockView;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.i(TAG , "-----onViewReleased 结束 -----");
                super.onViewReleased(releasedChild, xvel, yvel);
                progressbar.setGaoLiang(false);
                moveHeight = 0f ;
                upTime = System.currentTimeMillis();
                leadTime =  upTime - downTime ;
//                Log.i(TAG, "----- 时间差：" + leadTime );
                if ((upTime - downTime ) <= 1000){
                    isOnClick = true ;
                    if (mClickListener != null ){
                        if (!isMove && !isRun) {
                            Log.i(TAG, "----- 我的按钮被点击了 -----");
                            mClickListener.onClick();
                        }
                    }
                }else {
                    isOnClick = false ;
                }

                int movedDistance = releasedChild.getTop() + releasedChild.getHeight();
                if (movedDistance >= getHeight() * unlockTriggerValue) {
//                    Log.i(TAG, "-----onViewReleased 到终点-----");
//                    //到终点
//                    animToYToPosition(releasedChild, getHeight() - mLockView.getHeight(), animationTimeDuration);
                        stopTouch(false);
                    if (mClickListener != null ){
                        if (isMove) {
                            mClickListener.onMoveClick();
                        }
                    }
                } else {
//                    Log.i(TAG, "-----onViewReleased 到起始点-----");
//                    //回到起点
                    animToYToPosition(releasedChild, 0, animationTimeDuration , isOnClick);
                }

            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.i(TAG , "-----clampViewPositionVertical 滑动 ----- (isMove && !isRun):" + (isMove && !isRun) + "-left:" + child.getLeft() + "-top:" + top+ "-right:" + child.getLeft() + child.getWidth()+ "-bottom:" + top + child.getHeight());
                if (isMove && !isRun) {
                    isShowBg = true;
                    if (mTouchBring != null){
                        mTouchBring.bringToFront();
                    }
                    final int oldTop = child.getTop();
                    int minY = 0;
                    int maxY = getHeight() - mLockView.getHeight();
                    if (top > minY && top < maxY) {
                        child.layout(child.getLeft(), top, child.getLeft() + child.getWidth(), top + child.getHeight());
                        moveHeight = top + child.getHeight() - child.getWidth() / 2;
                    }

                    return oldTop ;
                }else {
                    return child.getTop() ;
                }
            }

        });

        // 虚线圆
//        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle = new Paint();
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(Color.WHITE);
        mPaintCircle.setStrokeWidth(3);

        // 虚线带三角
//        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine = new Paint();
        mPaintLine.setStyle(Paint.Style.FILL);
        mPaintLine.setColor(Color.WHITE);
        mPaintLine.setStrokeWidth(5);
        // 虚线带三角
//        mPaintMask = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintMask = new Paint();
        mPaintMask.setColor( Color.WHITE );
        mPaintMask.setStyle(Paint.Style.STROKE);
        mPaintMask.setStrokeWidth(15);

        // 字体
//        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText = new Paint();
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(40f);
        // 文字水平居中
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG , "onInterceptTouchEvent 触摸");
        if (mTouchBring != null){
            mTouchBring.bringToFront();
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST);

        this.mLockView = getChildAt(0);

        mLockView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        int lockHeight = mLockView.getMeasuredHeight();
        int lockWidth = mLockView.getMeasuredWidth();
//        int height = 0;
//        int width = lockWidth;

//        switch (MeasureSpec.getMode(heightMeasureSpec))  {
//            case MeasureSpec.EXACTLY:
//                height = MeasureSpec.getSize(widthMeasureSpec) ;
//                break;
//            default:
//                height = lockHeight ;
//                break;
//        }
//        setMeasuredDimension(width, height);
        setMeasuredDimension(lockWidth, childHeightMeasureSpec);


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i(TAG, "----- dispatchDraw -----");
        // 中间段
        mRoundRect.left = 0;
        mRoundRect.top = 0 + getWidth()/2 ;
        mRoundRect.bottom = getHeight() - getWidth()/2 ;
        mRoundRect.right = getWidth();
        // 下方
        mRoundArcTop.left = 0;
        mRoundArcTop.top = getHeight() - getWidth() ;
        mRoundArcTop.right = getWidth();
        mRoundArcTop.bottom = getHeight();
        // 上方
        mRoundArcBottom.left = 0;
        mRoundArcBottom.top = 0 ;
        mRoundArcBottom.right = getWidth();
        mRoundArcBottom.bottom =  getWidth() ;



        if (isShowBg) {
            // 显示背景透明色
//        mBackView.setVisibility(View.GONE);

            drawBg(canvas);


            // 绘制可以消失的箭头
            float startYLine = getWidth();// 起始位置
            float centerXLine = getWidth() / 2;// x轴中间位置
            float endYLine = getHeight() - getWidth();// 结束位置
            float lineHeight = (endYLine - startYLine) / 4;// 每一段长度
            // 没个线的间隔
            float paddingLine = 10f;
            // 计算三角形变长
            float sjxLength = (float) (lineHeight - paddingLine / Math.abs(Math.sin(Math.PI / 3)));

            // 第一条线
//        Log.i(TAG, "----- moveHeight:"+ moveHeight);
            if (moveHeight <= (startYLine + lineHeight)) {
                canvas.drawLine(centerXLine, startYLine + paddingLine, centerXLine, startYLine + lineHeight, mPaintLine);
            }
            // 第二条线
            if (moveHeight <= (startYLine + lineHeight * 2)) {
                canvas.drawLine(centerXLine, startYLine + paddingLine + lineHeight, centerXLine, startYLine + lineHeight * 2, mPaintLine);
            }
            // 第三条线
            if (moveHeight <= (startYLine + lineHeight * 3)) {
                canvas.drawLine(centerXLine, startYLine + paddingLine + lineHeight * 2, centerXLine, startYLine + lineHeight * 3, mPaintLine);
            }
            // 绘制这个三角形,你可以绘制任意多边形
            if (moveHeight <= (startYLine + lineHeight * 4)) {
                Path path = new Path();
                path.moveTo(centerXLine - sjxLength / 2, startYLine + lineHeight * 3);// 此点为多边形的起点
                path.lineTo(centerXLine, startYLine + lineHeight * 4 - paddingLine);
                path.lineTo(centerXLine + sjxLength / 2, startYLine + lineHeight * 3);
                path.close(); // 使这些点构成封闭的多边形
                canvas.drawPath(path, mPaintLine);
            }

            // 覆盖层
            if (moveHeight >= getHeight() - getWidth()/2 -3 ) {
                float cPadding = 25f;// 虚线圆距离底部边框距离
                float cRadius = getWidth() / 2 - cPadding;
                float cx = getWidth() / 2;
                float cy = getHeight() - cPadding - cRadius;
                setLayerType(LAYER_TYPE_SOFTWARE, null);
//             Blur.INNER(内发光)、Blur.SOLID(外发光)、Blur.NORMAL(内外发光)、Blur.OUTER(仅发光部分可见)
//            mPaintMask.setMaskFilter(new BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID));
                mPaintMask.setShadowLayer(20, 0, 0, Color.WHITE);
                canvas.drawCircle(cx, cy, cRadius , mPaintMask);
                progressbar.setGaoLiang(true);
            }else{
                progressbar.setGaoLiang(false);
            }

//            Bitmap mBottom = BitmapFactory.decodeResource(mContext.getResources() , R.drawable.eb_switch_bottom_icon);
//            canvas.drawBitmap(mBottom , mRectW , mRectW , mPaintMask );

        }
        super.dispatchDraw(canvas);
    }

    /**
     * 绘制背景状态
     */
    public void drawBg(Canvas canvas){

        // 绘制背景
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.anim_black_bg));
        canvas.drawRect(mRoundRect, mPaint);

        // 绘制下方圆弧
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.anim_black_bg));
        canvas.drawArc(mRoundArcTop, 0, 180, false, mPaint);

        // 绘制上方圆弧
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.anim_black_bg));
        canvas.drawArc(mRoundArcBottom, 0, -180, false, mPaint);



        // 绘制圆虚线
        float cPadding = 25f;// 虚线圆距离底部边框距离
        float cRadius = getWidth() / 2 - cPadding;
        float cx = getWidth() / 2;
        float cy = getHeight() - cPadding - cRadius;
        PathEffect dashPathEffect = new DashPathEffect(new float[]{10, 6}, 1);
        mPaintCircle.setPathEffect(dashPathEffect);
        // 虚线圆
        canvas.drawCircle(cx, cy, cRadius, mPaintCircle);


        // 画关闭字体
        String drawMsg = "关 闭";
        Rect bounds = new Rect(); // 文字边框
        mPaintText.getTextBounds(drawMsg, 0, drawMsg.length(), bounds); // 获得绘制文字的边界矩形
        Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt(); // 获取绘制Text时的四条线
//        int center = getWidth() / 2 ;
//        int baseline = getHeight() - center + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom; // 计算文字的基线,方法见http://blog.csdn.net/harvic880925/article/details/50423762
        float baseline = mRoundArcTop.top + (mRoundArcTop.bottom - mRoundArcTop.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(drawMsg, mRoundArcTop.centerX(), baseline, mPaintText); // 绘制表示进度的文字

        drawBg = true ;

        canvas.save() ;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int viewHeight = b - t;

        int lockViewWidth = mLockView.getMeasuredWidth();
        int lockViewHeight = lockViewWidth ;

//        mLockView.layout(0,  viewHeight - lockViewHeight  , lockViewWidth,  viewHeight  );
        Log.i(TAG , "----- onLayout mLockView -----isShowBg:" + isShowBg);
        if (!isShowBg) {
            mLockView.layout(0, 0, lockViewWidth, lockViewHeight);
        }
//        mBackView.layout(0,  0 , r - l,  b - t );
    }
    /**
     * 使用动画转到指定的位置
     *
     * @param view 需要作用动画的视图
     * @param toY  需要转到的位置
     */
    public void animToYToPosition(final View view, int toY, long animationTime , final boolean onClick) {
        Property<View, Integer> layoutProperty = new Property<View, Integer>(Integer.class, "layout") {

            @Override
            public void set(View object, Integer value) {
                Log.i(TAG , "----- animToYToPosition mLockView -----");
                object.layout((getWidth() - mLockView.getWidth() ) / 2              , value,
                        (getWidth() - mLockView.getWidth()) / 2 + object.getWidth() , value + object.getHeight() );

                moveHeight =  value + object.getHeight() - object.getWidth()/2 ;
            }

            @Override
            public Integer get(View object) {
                return view.getTop() ;
            }
        };

        //原来的动画正在执行
        //取消掉，防止多重动画冲突
        if (oa != null && oa.isRunning())
        {
            oa.cancel();
        }
        oa = ObjectAnimator.ofInt(view, layoutProperty, view.getTop() , toY);
        oa.setInterpolator(new AccelerateInterpolator());
        oa.setDuration(animationTime);
        oa.start();
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i(TAG, "----- 动画执行结束 -----");
                stopTouch(onClick);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                Log.i(TAG, "----- 动画执行取消 -----");
                stopTouch(onClick);
            }
        });

    }

    /**
     * 结束各种动画操作
     */
    public void stopTouch(boolean onClick) {
        if (onClick){
            mLockView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopTouch(false) ;
                }
            } , 1000) ;
        }else {
            isShowBg = false;
            // 结束触摸的时候换到底层
            if (mTouchBring != null) {
                mTouchBring.bringToBack();
            }
            // 设置按钮位置在初始位置
            int lockViewWidth = mLockView.getMeasuredWidth();
            int lockViewHeight = lockViewWidth;
            Log.i(TAG , "----- stopTouch mLockView -----");
            mLockView.layout(0, 0, lockViewWidth, lockViewHeight);

            postInvalidate();
        }
    }

    public interface SlideOnClickListener{
        /**点击事件*/
        void onClick();
        /**滑动到底部的事件*/
        void onMoveClick();
    }
    public interface OnTouchBring{
        /** 将布局移动到最上层 */
        void bringToFront() ;
        /** 将布局移动到下层 */
        void bringToBack() ;
    }


    /***************** 下方这一块为CarStartAnimation3 操作部分 *******************/
    /** 获取背景图片 */
    public ImageView getStartStatusBg() {
        return startStatusBg;
    }
    /** 获取动画按钮 */
    public CarStartAnimation3 getProgressbar() {
        return progressbar;
    }
    /** 获取文字 */
    public TextView getStartStatusText() {
        return startStatusText;
    }

    /**
     * 初始化电门动画进度条
     */
    public void initProgressbar(CarStartAnimation3.OnCountdownProgressListener mCountdownProgressListener){
        progressbar = getProgressbar() ;

        progressbar.setProgressLineWidth(15);
        progressbar.setTimeMillis(1000);
        progressbar.setOutLineColor(Color.TRANSPARENT);
        progressbar.setProgress(0);
        progressbar.isSetTimeout(true);
        progressbar.setTimeout(blueTimeout);

        progressbar.setCountdownProgressListener(1,mCountdownProgressListener );
    }

    /** 设置已启动状态 */
    public void setOpenStatus() {
        progressbar.setProgress(0);
        progressbar.stop();
        progressbar.setProgressBgColor( getResources().getColor(R.color.color_end_gradient));
        progressbar.setProgressColor( getResources().getColor(R.color.color_768392));
        startStatusBg.setImageResource(R.drawable.home_car_no_start_image);
        startStatusText.setText("已启动");
        setIsMove(true);
        setRun(false);
        if (!isShowBg) {
            setMoveHeight(0f);
            postInvalidate();
        }
    }

    /** 设置未启动状态 */
    public  void setCloseStatus(){
        progressbar.setProgress(0);
        progressbar.stop();
        progressbar.setProgressBgColor( getResources().getColor(R.color.color_768392));
        progressbar.setProgressColor( getResources().getColor(R.color.color_end_gradient));
        startStatusBg.setImageResource(R.drawable.home_car_no_end_image);
        startStatusText.setText("未启动");
        setIsMove(false);
        setRun(false);
        if (!isShowBg) {
            setMoveHeight(0f);
            postInvalidate();
        }
    }

    /**
     *  立即结束动画
     */
    public void stopAnimTime(){
        progressbar.setTimeoutCount( true ,  3 );
    }

    /**
     * 启动动画
     */
    public void startAnimStatus(){
        progressbar.setProgressType(CarStartAnimation3.ProgressType.COUNT);
        progressbar.reStart();
        startStatusText.setText("启动中");
        setRun(true);
    }

    /**
     * 关闭动画
     */
    public void stopAnimStatus(){
        progressbar.setProgressType(CarStartAnimation3.ProgressType.COUNT);
        progressbar.reStart();
        startStatusText.setText("关闭中");
        setRun(true);
    }

    /**
     *  暂停动画
     */
    public void pauseAnimStatus(){
        progressbar.stopProgress(CarStartAnimation3.ProgressType.PROGRESS_STOP);
    }

    /**
     *  恢复动画
     */
    public void resumeAnimStatus(){
        progressbar.stopProgress(CarStartAnimation3.ProgressType.COUNT);
    }

    /***************** 下方这一块为CarStartAnimation3 操作部分 *******************/

}
