package frame.zzt.com.appframe.signed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.R;

/**
 * 自定义签到
 * Created by zeting
 * Date 19/7/15.
 */
public class MySignedView extends View {
    private static final String TAG = MySignedView.class.getSimpleName() ;

    private static final float DEF_HEIGHT = 85f; //默认高度
    private static final float LINE_HEIGHT = 6f ; // 线段高度
    private static final float ICON_WIDTH = 24f ; // 显示天数点宽
    private static final float ICON_HEIGHT = 24f ; // 显示天数点高度
    private static final float UP_WIDTH = 20f ; // 显示礼物 宽
    private static final float UP_HEIGHT = 20f ; // 显示礼物高度
    private static final float BG_HEIGHT = 30f ; // 背景高度
    private static final float PADDING_LEFT_RIGHT = 20f ; // 背景距离左右边距
    private static final float SIGN_POINT_PADDINGLR = 20f ; // 日期点距离左右边距
    private static final float UP_PADDING = 10f ; // 礼物和背景间距
    private static final float TEXT_PADDING = 10f ; // 文字和背景间距
    private static final float TEXT_DAY_SIZE = 12f ; // 文字大小


    /**
     * 线段的高度
     */
    private float mLineHeight ;
    /**
     * 图标宽度
     */
    private float mIconWidth  ;
    /**
     * 图标的高度
     */
    private float mIconHeight ;
    /**
     * 上面图标宽度
     */
    private float mUpWidth  ;
    /**
     * 上面图标的高度
     */
    private float mUpHeight  ;
    /**
     * 线段长度
     */
    private float mLineWidth ;
    /**
     * 背景高度
     */
    private float mBgHeight ;
    /**
     * 背景距离左右边距
     */
    private float mPaddingLeftRight ;
    /**
     * 签到点距离左右边距
     */
    private float mSignPointPaddingLR ;
    /**
     * 图标中心点Y
     */
    private float mSignCenterY ;
    /**
     * 礼物和背景间距
     */
    private float mUpPadding ;
   /**
     * 文字和背景间距
     */
    private float mTextPadding ;


    /**
     * 数据源
     */
    private List<SignBean> mSignBeanList;
    private int mStepNum = 0;

    /**
     * 图标中心点位置
     */
    private List<Float> mCircleCenterPointPositionList;


    private int viewHeight;  //控件高度
    private int viewWidth; //控件宽度
    private float pointViewWidth; // 签到点所占左右控件宽度
    private float startPoint ; // 起始点坐标位置
    private float averageWidth ; // 每一段平均宽度

    /**
     * 签到进度背景
     */
    private Paint signInBgPaint;  //签到背景  画笔
    private RectF signInBgRectF; //整个签到背景线条区域
    private int signInBgColor = ContextCompat.getColor(getContext(), R.color.red );  //签到背景颜色

    /**
     * 日期字段画笔
     */
    private TextPaint mTextDayPaint;
    private int mTextDayColor = ContextCompat.getColor(getContext(), R.color.orange );  //签到背景颜色
    private float mTextDaySize ;

    /**
     * 绘制日志轴线
     */
    private Paint signDayPaint;  //签到日期  画笔
    private int signDayStartColor = Color.parseColor("#FFBA00") ;
    private int signDayEndColor = Color.parseColor("#FF7200") ;

    /**
     * 已经完成的图标
     */
    private Drawable mCompleteIcon;
    /**
     * 正在进行的图标
     */
    private Drawable mAttentionIcon;
    /**
     * 默认的图标
     */
    private Drawable mDefaultIcon;
    /**
     * UP图标
     */
    private Drawable mUpIcon;

    private Context mContext ;


    public MySignedView(Context context) {
        super(context);
        initView(context) ;
    }

    public MySignedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context) ;
    }

    public MySignedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context) ;
    }

    private void initView(Context con ){
        this.mContext = con ;

        mSignBeanList = new ArrayList<>();

        mCircleCenterPointPositionList = new ArrayList<>();

        //绘制背景画笔
        signInBgPaint = new Paint();
        signInBgPaint.setAntiAlias(true);
        signInBgPaint.setColor(signInBgColor);
        signInBgPaint.setStrokeWidth(2);
        signInBgPaint.setStyle(Paint.Style.FILL);

        // 字体大小
        mTextDaySize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP , TEXT_DAY_SIZE , getResources().getDisplayMetrics());
        // 绘制文字画笔
        mTextDayPaint = new TextPaint();
        mTextDayPaint.setAntiAlias(true);
        mTextDayPaint.setColor(mTextDayColor);
        mTextDayPaint.setStyle(Paint.Style.FILL);
        mTextDayPaint.setTextSize( mTextDaySize );
        mTextDayPaint.setTextAlign( Paint.Align.CENTER );

        // 日期轴线段高度
        mLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_HEIGHT , getResources().getDisplayMetrics());
        // 设置日志轴线画笔
        signDayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        signDayPaint.setStyle(Paint.Style.FILL);
//        signInBgPaint.setStrokeWidth(mLineHeight);


        //已经完成的icon
        mCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.sign);
        //正在进行的icon
        mAttentionIcon = ContextCompat.getDrawable(getContext(), R.drawable.progress);
        //未完成的icon
        mDefaultIcon = ContextCompat.getDrawable(getContext(), R.drawable.unsign);
        //UP的icon
        mUpIcon = ContextCompat.getDrawable(getContext(), R.drawable.jifendikuai);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int newHeight;
        //如果不是精准模式   就使用默认的高度      具体用法请百度 MeasureSpec.getMode()
        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            newHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_HEIGHT, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 整个view的宽高
        viewWidth = w;
        viewHeight = h;

        setChange( );
    }

    private void setChange( ) {
        // 初始化默认值
        mIconWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ICON_WIDTH , getResources().getDisplayMetrics());
        mIconHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ICON_HEIGHT , getResources().getDisplayMetrics());
        mUpWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, UP_WIDTH , getResources().getDisplayMetrics());
        mUpHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, UP_HEIGHT , getResources().getDisplayMetrics());
        mBgHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BG_HEIGHT , getResources().getDisplayMetrics());
        mPaddingLeftRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING_LEFT_RIGHT , getResources().getDisplayMetrics());
        mSignPointPaddingLR = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SIGN_POINT_PADDINGLR , getResources().getDisplayMetrics());
        mUpPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, UP_PADDING , getResources().getDisplayMetrics());
        mTextPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TEXT_PADDING , getResources().getDisplayMetrics());


        // 签到背景图片
        signInBgRectF = new RectF(0 + mPaddingLeftRight, mUpHeight + mUpPadding , viewWidth - mPaddingLeftRight , mUpHeight + mUpPadding + mBgHeight );

        // 签到Y轴中心点
        mSignCenterY = mUpHeight + mUpPadding + mBgHeight * 0.5f ;

        calcucateCirclePoints( mSignBeanList ) ;
    }

    /**
     * 计算每个图标中心点位置
     * @param mSignBeanList
     */
    private void calcucateCirclePoints( List<SignBean> mSignBeanList ) {
        if (mSignBeanList != null && mSignBeanList.size() > 0 ){
            // 设置显示个数
            mStepNum = mSignBeanList.size();
            // 起始点坐标位置
            startPoint = mPaddingLeftRight + mSignPointPaddingLR ;
            // 所有点所占宽度
            pointViewWidth = viewWidth - mPaddingLeftRight * 2f - mSignPointPaddingLR * 2f ;
            // 水平均分个数
            int intervalSize = mStepNum - 1;
            // 每一段平均宽度
            averageWidth = pointViewWidth / intervalSize ;

            // 计算每个天数点的中心坐标
            mCircleCenterPointPositionList.clear();
            for (int i = 0; i < mStepNum ; i++) {
                mCircleCenterPointPositionList.add( startPoint + averageWidth * i );
            }

        }
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 签到背景
        drawSignInBgRect( canvas );
        // 日期轴
        drawDayGradualRect( canvas );
        if (mSignBeanList.size() != 0) {
            // 签到天数点
            drawSignPointList ( canvas ) ;
        }

    }



    /**
     * 绘制签到天数点
     * @param canvas
     */
    private void drawSignPointList(Canvas canvas) {
        if (mCircleCenterPointPositionList != null && mCircleCenterPointPositionList.size() >0){
            for (int i = 0; i < mCircleCenterPointPositionList.size() ; i++ ) {
                /**
                 *  当前点中间点
                 */
                float currentComplectedXPosition = mCircleCenterPointPositionList.get(i);
                Rect rect = new Rect((int) (currentComplectedXPosition - mIconWidth / 2),
                        (int) (mSignCenterY - mIconHeight / 2),
                        (int) (currentComplectedXPosition + mIconWidth / 2),
                        (int) (mSignCenterY + mIconHeight / 2));

                /**
                 *  当前点对象
                 */
                SignBean signBean  = mSignBeanList.get(i);

                if (signBean.getState() == SignBean.STEP_UNDO) {
                    mDefaultIcon.setBounds(rect);
                    mDefaultIcon.draw(canvas);
                } else if (signBean.getState() == SignBean.STEP_CURRENT) {
                    mAttentionIcon.setBounds(rect);
                    mAttentionIcon.draw(canvas);
                } else if (signBean.getState() == SignBean.STEP_COMPLETED) {
                    mCompleteIcon.setBounds(rect);
                    mCompleteIcon.draw(canvas);
                }

                /**
                 * 绘制上面礼物图片
                 */
                if (signBean.getNumber() != 0) {
                    //需要UP才进行绘制
                    Rect rectUp =  new Rect((int) (currentComplectedXPosition - mUpWidth / 2) , 0 ,
                                    (int) (currentComplectedXPosition + mUpWidth / 2) , (int) mUpHeight);
                    mUpIcon.setBounds(rectUp);
                    mUpIcon.draw(canvas);
                }

                /**
                 * 绘制文字
                 */
                String textStr = signBean.getDay() ;
                Paint.FontMetrics fontMetrics = mTextDayPaint.getFontMetrics();
                float top = fontMetrics.top ;
                canvas.drawText(textStr,
                        currentComplectedXPosition ,
                        mUpHeight + mUpPadding + mBgHeight + mTextPadding - top ,
                        mTextDayPaint);


            }
        }
    }

    /**
     * 绘制渐变日期轴
     * @param canvas
     */
    private void drawDayGradualRect(Canvas canvas) {
        /**
         * 绘制日期轴渐变
         */
        float x0 = 0 + mPaddingLeftRight ;
        float x1 = viewWidth - mPaddingLeftRight ;
        float y0 = mUpHeight + mUpPadding + mBgHeight*0.5f - mLineHeight * 0.5f ;
        float y1 = mUpHeight + mUpPadding + mBgHeight*0.5f + mLineHeight * 0.5f ;

        // 设置圆角
        float radiusx = mLineHeight * 0.5f ;

        signDayPaint.setShader(
                new LinearGradient(x0 , y0 , x1 , y1 ,
                        signDayStartColor ,
                        signDayEndColor ,
                        android.graphics.Shader.TileMode.MIRROR));
        canvas.drawRoundRect(
                new RectF(Math.round( x0 ), Math.round( y0 ),
                        Math.round( x1 ), Math.round( y1 )),
                radiusx , radiusx , signDayPaint);
        signDayPaint.setShader(null) ;
    }

    /**
     * 绘制签到背景
     * @param canvas
     */
    private void drawSignInBgRect(Canvas canvas) {

        canvas.drawRect(signInBgRectF, signInBgPaint);
    }


    /**
     * 设置流程步数
     *
     * @param singBeanList 流程步数
     */
    public void setStepNum(List<SignBean> singBeanList) {

        if (singBeanList == null && singBeanList.size() == 0) {
            return;
        }
        mSignBeanList = singBeanList;
        setChange();//重新绘制

        //引起重绘
        postInvalidate();
    }


}
