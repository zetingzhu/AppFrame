package frame.zzt.com.appframe.slidelock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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
 * 水平滑动控件
 * Created by zeting
 * Date 19/4/23.
 */

public class SlideLockHorizontalView extends ViewGroup {
    private static final String TAG = "SlideLock/Slh" ;

    private ViewDragHelper viewDragHelper;

    private Paint mPaint;// 绘制背景的
    private Paint mPaintText;// 绘制 字体
    private Paint mPaintText1;// 绘制 字体
    private Paint mPaintText2;// 绘制 字体
    private Paint mPaintText3;// 绘制 字体
    private Paint mPaintArrow ;// 绘制 字体
    private Paint mPaintMoveBg ;// 移动下发的遮罩成

    private String drawMsg = "拖动按钮至左端";
    private String drawMsg1 = "拖动";
    private String drawMsg2 = "按钮至";
    private String drawMsg3 = "左端";
    private Rect bounds  ;// 文字的大小
    private Rect bounds1 ;// 文字的大小
    private Rect bounds2 ;// 文字的大小
    private Rect bounds3 ;// 文字的大小
    private Paint.FontMetricsInt fontMetrics ;// 文字测量

    private float textSize = getResources().getDimension(R.dimen.text_size_12) ;
    private int textColor  = Color.parseColor("#a8aef6") ;

    private Bitmap  arrowBitmap ; //箭头图片

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
    private float moveWidth  = 0f ;
    /** 是否属于点击事件 */
    private boolean isOnClick = false ;
    /**是否正在执行 这个状态没用了 */
    private boolean isRun = false ;
    /**是否已经结束了这次动画*/
    private boolean isMoveBotton = false ;
    // 动画
    private SlideLockProgress progressbar; // 蓝牙车辆启动动画
    private ImageView startStatusBg; // 蓝牙车辆启动动画
    private TextView startStatusText; // 蓝牙车辆启动动画

    private long blueTimeout = 10 * 1000 ;// 执行蓝牙超时时间


    public SlideLockHorizontalView(Context context) {
        this(context, null);
    }

    public SlideLockHorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLockHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context ;
        initView();
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
        progressbar = (SlideLockProgress) findViewById(R.id.custom_car_start_status);
        startStatusBg = (ImageView) findViewById(R.id.iv_car_start_status_bg);
        startStatusText = (TextView) findViewById(R.id.tv_car_start_status);
    }

    private void initView() {

        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Log.i(TAG , "-----tryCaptureView 开始 ----- child == mLockView:" + (child == mLockView) );
                // 当 child 是 mHomeView 时，才允许捕获。
                downTime = System.currentTimeMillis() ;
                if (isMove && !isRun) {
                    isShowBg = true;
                }else {
                    isShowBg = false;
                }
                moveWidth = child.getRight() - (child.getWidth()/2 ) ;
                isMoveBotton = false ;
                postInvalidate();

                return child == mLockView;
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.i(TAG , "-----onViewReleased 结束 -----");
                super.onViewReleased(releasedChild, xvel, yvel);
                progressbar.setGaoLiang(false);
                moveWidth = releasedChild.getRight() - (releasedChild.getWidth()/2 ) ;
                upTime = System.currentTimeMillis();
                leadTime =  upTime - downTime ;
//                Log.i(TAG, "----- 时间差：" + leadTime );
                if ((upTime - downTime ) <= 1000){
                    isOnClick = true ;
                    if (mClickListener != null ){
                        if (!isMove && !isRun) {
//                            Log.i(TAG, "----- 我的按钮被点击了 -----");
                            mClickListener.onClick();
                        }
                    }
                }else {
                    isOnClick = false ;
                }

                if (!isMoveBotton) {
                    int movedDistance = releasedChild.getTop() + releasedChild.getHeight();
                    if (movedDistance >= getHeight() * unlockTriggerValue) {
                        setMoveBotttomClick();

                    } else {
                        animToYToPosition(releasedChild, 0, animationTimeDuration , isOnClick);
                    }
                    isMoveBotton = true;
                } else {
                    setMoveBotttomClick();
                }

            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                int oldLeft = child.getLeft() ;
                int minX = 0;
                int maxX = getWidth() - mLockView.getWidth() ;
                if (left > minX && left < maxX) {
                    Log.i(TAG , "-----clampViewPositionVertical 滑动 -----  left:" + left + "-dx:" +dx + "- oldLeft:" + oldLeft );
                    child.layout( left  , child.getTop() , left + child.getWidth() , child.getBottom());
                    moveWidth = left + ( child.getWidth()/2 ) ;
                }
                return oldLeft ;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }
        });

        // 背景
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor( Color.parseColor("#d81c1e2d" ) );//透明背景色



//        mPaintText.getTextBounds(drawMsg , 0, drawMsg .length(), bounds ); // 获得绘制文字的边界矩形


        // 字体
        mPaintText1 = new Paint( );

        mPaintText1.setAntiAlias(true);
        mPaintText1.setStyle(Paint.Style.FILL);
        mPaintText1.setColor( textColor );
        mPaintText1.setTextSize(textSize);
        // 文字水平居中
        mPaintText1.setTextAlign(Paint.Align.CENTER);
        bounds1 = new Rect(); // 文字边框
        mPaintText1.getTextBounds(drawMsg1, 0, drawMsg1.length(), bounds1); // 获得绘制文字的边界矩形

        mPaintText2 = new Paint( );
        // 设置画笔是否抗锯齿
        mPaintText2.setAntiAlias(true);
        mPaintText2.setStyle(Paint.Style.FILL);
        mPaintText2.setColor( textColor );
        mPaintText2.setTextSize(textSize);
        // 文字水平居中
        mPaintText2.setTextAlign(Paint.Align.CENTER);
        bounds2 = new Rect(); // 文字边框
        mPaintText2.getTextBounds(drawMsg2, 0, drawMsg2.length(), bounds2); // 获得绘制文字的边界矩形


        mPaintText3 = new Paint( );
        mPaintText3.setStyle(Paint.Style.FILL);
        mPaintText3.setAntiAlias(true);
        mPaintText3.setColor(textColor);
        mPaintText3.setTextSize( textSize);
        // 文字水平居中
        mPaintText3.setTextAlign(Paint.Align.CENTER);
        bounds3 = new Rect(); // 文字边框
        mPaintText3.getTextBounds(drawMsg3, 0, drawMsg3.length(), bounds3); // 获得绘制文字的边界矩形

        // 绘制所有字体
        mPaintText = new Paint( );
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor( textColor );
        mPaintText.setTextSize(textSize);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        bounds = new Rect() ;
        mPaintText.getTextBounds(drawMsg, 0, drawMsg.length(), bounds); // 获得绘制文字的边界矩形
        fontMetrics = mPaintText.getFontMetricsInt(); // 获取绘制Text时的四条线



        // 箭头
        mPaintArrow = new Paint( );
        mPaintArrow.setStyle(Paint.Style.FILL);

        if (arrowBitmap == null) {
            arrowBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bluetoothbtn_arrow );
        }

        // 移动遮罩成
        mPaintMoveBg = new Paint( );
        mPaintMoveBg.setStyle(Paint.Style.FILL);
        mPaintMoveBg.setColor( Color.parseColor("#d81c1e2d") );

    }

    /**
     * 设置滑动到底部监听
     */
    public void setMoveBotttomClick(){
        stopTouch(false);
        if (mClickListener != null ){
            if (isMove) {
                mClickListener.onMoveClick();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Logger.debug(TAG , "onInterceptTouchEvent 触摸");
        if (mTouchBring != null){
            mTouchBring.bringToFront();
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.EXACTLY);

        this.mLockView = getChildAt(0);

        mLockView.measure(childHeightMeasureSpec, childHeightMeasureSpec);
        int lockHeight = mLockView.getMeasuredHeight();
        int lockWidth = mLockView.getMeasuredWidth();

        setMeasuredDimension( childWidthMeasureSpec , lockHeight );
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        Log.i(TAG, "----- dispatchDraw -----isShowBg:" + isShowBg);

        // 中间段
        mRoundRect.left = 0;
        mRoundRect.top = 0 ;
        mRoundRect.bottom = getHeight() ;
        mRoundRect.right = getWidth();

        if (isShowBg) {

            Log.w(TAG , "拖动中间信息：" + moveWidth );

            // 绘制背景
            canvas.drawRoundRect( mRoundRect , getResources().getDimension(R.dimen.dimen_7dp)  , getResources().getDimension(R.dimen.dimen_7dp) , mPaint);

            // 画拖动字体
            float textX = ( mRoundRect.right - mLockView.getWidth() ) * 0.5f ;
            float baseline = mRoundRect.top + (mRoundRect.bottom - mRoundRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            // 中间
            if (moveWidth > textX ) {
                canvas.drawText(drawMsg2, textX, baseline, mPaintText2); // 绘制表示进度的文字
            }
            // 左边
            float textXLeft = textX - bounds2.width()*0.5f - bounds1.width()*0.5f - 4 ;
            if (moveWidth > textXLeft ) {
                canvas.drawText(drawMsg1, textXLeft , baseline, mPaintText1); // 绘制表示进度的文字
            }
            // 右边
            float textXRight = textX + bounds2.width() * 0.5f + bounds3.width() * 0.5f + 4;
            if (moveWidth > textXRight ) {
                canvas.drawText(drawMsg3, textXRight , baseline, mPaintText3); // 绘制表示进度的文字
            }

//            canvas.drawText(drawMsg , textX  , baseline - 80, mPaintText ); // 绘制表示进度的文字


            // 获取字符串的宽度
            float textWidth = bounds.width() ;
            float textY =   mLockView.getWidth()   * 0.5f ;
            // 获取文件左边，右边距离
            float textPaddingLeft = textX - textWidth * 0.5f ;
            float textPaddingRight = textX + textWidth * 0.5f ;
            float textHeight = bounds2.height() ;
            float bitmapWidth = textHeight/2 ;// 图片宽
            float bitmapPadding = 5 ;// 箭头间距
            float textPadding = 35 ;// 文字离箭头间距

            // 绘制图片
            // 背景图片显示区域
            RectF mRectArrowLeft1 = new RectF(textPaddingLeft - textPadding- bitmapWidth                          , textY -  textHeight * 0.5f, textPaddingLeft - textPadding                                       , textY + textHeight * 0.5f );
            RectF mRectArrowLeft2 = new RectF(textPaddingLeft - textPadding- bitmapWidth * 2  - bitmapPadding     , textY -  textHeight * 0.5f, textPaddingLeft - textPadding - bitmapWidth - bitmapPadding         , textY + textHeight * 0.5f );
            RectF mRectArrowLeft3 = new RectF(textPaddingLeft - textPadding- bitmapWidth * 3  - bitmapPadding * 2 , textY -  textHeight * 0.5f, textPaddingLeft - textPadding- bitmapWidth * 2  - bitmapPadding * 2 , textY + textHeight * 0.5f );
            RectF mRectArrowLeft4 = new RectF(textPaddingLeft - textPadding- bitmapWidth * 4  - bitmapPadding * 3 , textY -  textHeight * 0.5f, textPaddingLeft - textPadding- bitmapWidth * 3  - bitmapPadding * 3 , textY + textHeight * 0.5f );
            RectF mRectArrowLeft5 = new RectF(textPaddingLeft - textPadding- bitmapWidth * 5  - bitmapPadding * 4 , textY -  textHeight * 0.5f, textPaddingLeft - textPadding- bitmapWidth * 4  - bitmapPadding * 4 , textY + textHeight * 0.5f );
            RectF mRectArrowLeft6 = new RectF(textPaddingLeft - textPadding- bitmapWidth * 6  - bitmapPadding * 5 , textY -  textHeight * 0.5f, textPaddingLeft - textPadding- bitmapWidth * 5  - bitmapPadding * 5 , textY + textHeight * 0.5f );

            RectF mRectArrowRight1 = new RectF(textPaddingRight + textPadding                                         , textY -  textHeight * 0.5f, textPaddingRight + textPadding + bitmapWidth * 1                     , textY + textHeight * 0.5f );
            RectF mRectArrowRight2 = new RectF(textPaddingRight + textPadding + bitmapWidth * 1 + bitmapPadding       , textY -  textHeight * 0.5f, textPaddingRight + textPadding + bitmapWidth * 2 + bitmapPadding     , textY + textHeight * 0.5f );
            RectF mRectArrowRight3 = new RectF(textPaddingRight + textPadding + bitmapWidth * 2 + bitmapPadding * 2   , textY -  textHeight * 0.5f, textPaddingRight + textPadding + bitmapWidth * 3 + bitmapPadding * 2 , textY + textHeight * 0.5f );
            RectF mRectArrowRight4 = new RectF(textPaddingRight + textPadding + bitmapWidth * 3 + bitmapPadding * 3   , textY -  textHeight * 0.5f, textPaddingRight + textPadding + bitmapWidth * 4 + bitmapPadding * 3 , textY + textHeight * 0.5f );
            RectF mRectArrowRight5 = new RectF(textPaddingRight + textPadding + bitmapWidth * 4 + bitmapPadding * 4   , textY -  textHeight * 0.5f, textPaddingRight + textPadding + bitmapWidth * 5 + bitmapPadding * 4 , textY + textHeight * 0.5f );
            RectF mRectArrowRight6 = new RectF(textPaddingRight + textPadding + bitmapWidth * 5 + bitmapPadding * 5   , textY -  textHeight * 0.5f, textPaddingRight + textPadding + bitmapWidth * 6 + bitmapPadding * 5 , textY + textHeight * 0.5f );


            Rect mRectBitmap = new Rect( 0 , 0 , arrowBitmap.getWidth() , arrowBitmap.getHeight() );
            if (moveWidth > (textPaddingLeft - textPadding- bitmapWidth) ) {
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowLeft1, mPaintArrow);
            }
            if (moveWidth > textPaddingLeft - textPadding- bitmapWidth * 2  - bitmapPadding  ) {
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowLeft2, mPaintArrow);
            }
            if (moveWidth > textPaddingLeft - textPadding- bitmapWidth * 3  - bitmapPadding * 2) {
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowLeft3, mPaintArrow);
            }
            if (moveWidth > textPaddingLeft - textPadding- bitmapWidth * 4  - bitmapPadding * 3) {
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowLeft4, mPaintArrow);
            }
            if (moveWidth > textPaddingLeft - textPadding- bitmapWidth * 5  - bitmapPadding * 4) {
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowLeft5, mPaintArrow);
            }
            if (moveWidth >  textPaddingLeft - textPadding- bitmapWidth * 6  - bitmapPadding * 5) {
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowLeft6, mPaintArrow);
            }

            if (moveWidth > textPaddingRight + textPadding){
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowRight1, mPaintArrow);
            }
            if (moveWidth > textPaddingRight + textPadding + bitmapWidth * 1 + bitmapPadding){
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowRight2, mPaintArrow);
            }
            if (moveWidth > textPaddingRight + textPadding + bitmapWidth * 2 + bitmapPadding * 2){
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowRight3, mPaintArrow);
            }
            if (moveWidth > textPaddingRight + textPadding + bitmapWidth * 3 + bitmapPadding * 3){
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowRight4, mPaintArrow);
            }
            if (moveWidth > textPaddingRight + textPadding + bitmapWidth * 4 + bitmapPadding * 4){
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowRight5, mPaintArrow);
            }
            if (moveWidth >  textPaddingRight + textPadding + bitmapWidth * 5 + bitmapPadding * 5){
                canvas.drawBitmap(arrowBitmap, mRectBitmap, mRectArrowRight6, mPaintArrow);
            }


            // 绘制下方遮罩层
//            Path pathBg  = new Path(); //定义一条路径
//            pathBg.moveTo(getWidth() , 0);
//            pathBg.lineTo(getWidth() , mLockView.getWidth()  );
//            pathBg.lineTo(moveWidth  , mLockView.getWidth() );
//            pathBg.lineTo(moveWidth  , 0 );
//            pathBg.lineTo(getWidth() , 0);
//
////            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);// 相交的地方绘制目标图像
////            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);// 相交的地方绘制目标图像 不可用
////            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);//[!取两图层全部区域，交集部分变为白色]不可用
////            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.XOR);//绘制S和D,相交的地方受到对应alpha和颜色值影响，如果都完全不透明则相交处不绘制完全透明,看效果图就知道了,相交的地方如果透明,则显示叠加效果
////            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//,绘制相交部分的S
//            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);//,表示输入色值和透明度都是0.就是完全透明显示
////            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.SRC);//只保留源图像的 先绘制的是目标图。
////            PorterDuffXfermode xfermodeAnim = new PorterDuffXfermode(PorterDuff.Mode.DST);//只保留了目标图像的
//            mPaintMoveBg.setXfermode(xfermodeAnim);
//            mPaintMoveBg.setAlpha(30);
//            canvas.drawPath(pathBg, mPaintMoveBg);
//            mPaintMoveBg.setXfermode(null);

        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int viewHeight = b - t;
        int viewWidth = r - l;

        int lockViewWidth = mLockView.getMeasuredWidth();
        int lockViewHeight = lockViewWidth ;

        Log.d(TAG , "-  int l:"+ l + " , int  t:"+ t + ", int  r:"+ r + ", int  b:"+ b );
        Log.i(TAG , "----- onLayout mLockView -----isShowBg:" + isShowBg);
        if (!isShowBg) {
            mLockView.layout( viewWidth - lockViewWidth , 0 , viewWidth , lockViewHeight);
        }
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
//                Log.i(TAG , "----- animToYToPosition mLockView -----");
                object.layout((getWidth() - mLockView.getWidth() ) / 2              , value,
                        (getWidth() - mLockView.getWidth()) / 2 + object.getWidth() , value + object.getHeight() );

                moveWidth  =  value + object.getHeight() - object.getWidth()/2 ;
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
//                Log.i(TAG, "----- 动画执行结束 -----");
                stopTouch(onClick);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
//                Log.i(TAG, "----- 动画执行取消 -----");
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

            mLockView.layout( getWidth() - lockViewWidth , 0 , getWidth() , lockViewHeight);

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


    /***************** 下方这一块为CarStartAnimation5 操作部分 *******************/
    /** 获取背景图片 */
    public ImageView getStartStatusBg() {
        return startStatusBg;
    }
    /** 获取动画按钮 */
    public SlideLockProgress getProgressbar() {
        return progressbar;
    }
    /** 获取文字 */
    public TextView getStartStatusText() {
        return startStatusText;
    }

    /**
     * 初始化电门动画进度条
     */
    public void initProgressbar(SlideLockProgress.OnCountdownProgressListener mCountdownProgressListener){
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
        progressbar.setProgressType(SlideLockProgress.ProgressType.COUNT);
        progressbar.reStart();
        startStatusText.setText("启动中");
        setRun(true);
    }

    /**
     * 关闭动画
     */
    public void stopAnimStatus(){
        progressbar.setProgressType(SlideLockProgress.ProgressType.COUNT);
        progressbar.reStart();
        startStatusText.setText("关闭中");
        setRun(true);
    }

    /**
     *  暂停动画
     */
    public void pauseAnimStatus(){
        progressbar.stopProgress(SlideLockProgress.ProgressType.PROGRESS_STOP);
    }

    /**
     *  恢复动画
     */
    public void resumeAnimStatus(){
        progressbar.stopProgress(SlideLockProgress.ProgressType.COUNT);
    }

    /***************** 下方这一块为CarStartAnimation5 操作部分 *******************/

}
