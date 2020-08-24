package frame.zzt.com.appframe.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.R;

/**
 * @author: zeting
 * @date: 2020/6/22
 * dialog 数据控制
 */
public class DialogConteroller {

    private final Context mContext;
    final AppCompatDialog mDialog;
    private final Window mWindow;

    // 左边按钮
    private Button mButtonLeft;
    CharSequence mButtonLeftText;
    Message mButtonLeftMessage;
    DialogWrapper.OnDialogViewClickListener leftClick;
    int leftButtonSize;
    @ColorInt
    int leftButtonColor;
    @ColorInt
    int leftBg;

    // 右边按钮
    private Button mButtonRight;
    CharSequence mButtonRightText;
    Message mButtonRightMessage;
    DialogWrapper.OnDialogViewClickListener rightClick;
    int rightButtonSize;
    @ColorInt
    int rightButtonColor;
    @ColorInt
    int rightBg;

    // 底部按钮布局的边距
    int bottomMarginLeft;
    int bottomMarginRight;
    int bottomMarginTop;
    int bottomMarginBottom;

    // 存在确定按钮时候，底部按钮布局
    private ConstraintLayout btn_layout;
    // 按钮顶部线 ,按钮中间线
    private View space_top, space_middle;

    // 两个按钮顶部背景间距
    int spaceButtonTop = 0;
    // 两个按钮中间间距
    int spaceButtonMiddle = 0;
    // 图片背景的圆角半径 默认为8 ，如果设置了弹框背景，就需要手动修改这个半径
    int cornerRadius = 8;
    // 是否底部一个按钮 单一
    boolean isBottomSingleBtn = false;

    // 最外层背景图片
    FrameLayout dialog_layout_bg;
    // 设置最外层背景
    @DrawableRes
    int dialog_layout_resid;
    // 里面内容背景图片，脱离删除按钮
    LinearLayout content_layout_bg;
    // 删除按钮
    AppCompatImageView dilog_img_close;
    // 顶部间距
    Space dialog_space_top;
    // 中间内容添加
    LinearLayout dialog_content_layout;
    // 底部间距
    Space dialog_space_bottom;
    // 默认的顶部间距高度
    int defauleTopSpace = 40;
    // 是否隐藏顶部间距，默认是显示顶部间距的
    boolean isHideTopSpace = false;
    // 顶部距离边缘距离
    int spaceMarginTop;
    // 底部距离边缘距离
    int spaceMarginBottom;
    // 左边边缘距离
    int spaceMarginLeft;
    // 右边边缘距离
    int spaceMarginRight;
    //所有添加的布局类型
    private List<View> mAddList;

    // 右上角删除
    Drawable delImg;
    DialogWrapper.OnDialogViewClickListener deleteClick;
    // 设置可以取消，监听
    public boolean mCancelable;
    public DialogInterface.OnCancelListener mOnCancelListener;
    public DialogInterface.OnDismissListener mOnDismissListener;

    // 监听事件
    private Handler mHandler;
    // 设置延迟消失事件
    long dialogDelayMillis;

    public Context getContext() {
        return mContext;
    }


    /**
     * 按钮点击事件
     */
    private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Message m;
            if (v == mButtonRight && mButtonRightMessage != null) {
                m = Message.obtain(mButtonRightMessage);
            } else if (v == mButtonLeft && mButtonLeftMessage != null) {
                m = Message.obtain(mButtonLeftMessage);
            } else {
                m = null;
            }

            if (m != null) {
                m.sendToTarget();
            }

            // 发布消息，以便我们在执行上述处理程序后将其解雇
            mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG, mDialog).sendToTarget();
        }
    };

    /**
     * 对话框消失
     */
    private static final class ButtonHandler extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private static final int BUTTON_LEFT = -1;
        private static final int BUTTON_RIGHT = -2;

        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialog) {
            mDialog = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BUTTON_LEFT:
                case BUTTON_RIGHT:
//                    ((DialogWrapper.OnDialogViewClickListener) msg.obj).onClick(mDialog.get(), v);
                    break;
                case MSG_DISMISS_DIALOG:
                    ((DialogInterface) msg.obj).dismiss();
                    break;
            }
        }
    }

    public DialogConteroller(Context mContext, AppCompatDialog mDialog, Window mWindow) {
        this.mContext = mContext;
        this.mDialog = mDialog;
        this.mWindow = mWindow;

        // 初始化布局列表
        mAddList = new ArrayList<>();

        mHandler = new ButtonHandler(mDialog);

        mDialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    /**
     * 初始化内容
     */
    public void installContent() {
        mDialog.setContentView(selectContentView());
    }

    /**
     * 选择内容视图
     *
     * @return
     */
    private View selectContentView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View baseView = inflater.inflate(R.layout.dialog_conteroller_layout, null);

        dialog_layout_bg = baseView.findViewById(R.id.dialog_layout_bg);
        content_layout_bg = baseView.findViewById(R.id.content_layout_bg);
        dilog_img_close = baseView.findViewById(R.id.dilog_img_close);
        dialog_space_top = baseView.findViewById(R.id.dialog_space_top);
        dialog_content_layout = baseView.findViewById(R.id.dialog_content_layout);
        dialog_space_bottom = baseView.findViewById(R.id.dialog_space_bottom);
        mButtonLeft = baseView.findViewById(R.id.btn_left);
        mButtonRight = baseView.findViewById(R.id.btn_right);
        btn_layout = baseView.findViewById(R.id.btn_layout);
        space_top = baseView.findViewById(R.id.space_top);
        space_middle = baseView.findViewById(R.id.space_middle);

        // 设置背景色
        if (dialog_layout_resid != 0) {
            dialog_layout_bg.setBackgroundResource(dialog_layout_resid);
        }

        // 删除按钮显示
        setRightDeleteClick(delImg, deleteClick);

        // 设置顶部边缘
        if (isHideTopSpace || (delImg == null && deleteClick == null)) {
            setSpaceMarginTop(spaceMarginTop);
        } else {
            setSpaceMarginTop(defauleTopSpace + spaceMarginTop);
        }
        // 设置底部边缘
        setSpaceMarginBottom(spaceMarginBottom);

        // 左右边缘距离
        if (spaceMarginLeft != 0 && spaceMarginRight != 0) {
            LinearLayout.LayoutParams contentParams = (LinearLayout.LayoutParams) dialog_content_layout.getLayoutParams();
            contentParams.setMargins(spaceMarginLeft, 0, spaceMarginRight, 0);
            dialog_content_layout.setLayoutParams(contentParams);
        }

        // 判断是否需要添加下方的按钮布局
        appendWButtonLayout(mButtonLeftText, mButtonRightText);

        // 添加中间布局
        for (View view : mAddList) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog_content_layout.addView(view, view.getLayoutParams());
        }

        return baseView;
    }


    /**
     * 是否显示删除按钮
     *
     * @param delImg
     * @param deleteClick
     */
    public void setRightDeleteClick(Drawable delImg, DialogWrapper.OnDialogViewClickListener deleteClick) {
        if (delImg != null) {
            dilog_img_close.setVisibility(View.VISIBLE);
            dilog_img_close.setBackgroundDrawable(delImg);
        }
        if (deleteClick != null) {
            dilog_img_close.setVisibility(View.VISIBLE);
            dilog_img_close.setOnClickListener(v -> {
                deleteClick.onClick(mDialog, v);
            });
        }
    }

    /**
     * 追加一个view
     *
     * @param view
     */
    public void appendView(@NonNull DialogWrapper.WFrameLayout view) {
        appendView(view, Gravity.CENTER_HORIZONTAL, 0, 0, 0, 0);
    }

    public void appendView(@NonNull DialogWrapper.WFrameLayout view, int layoutGravity, int left, int top, int right, int bottom) {
        appendView(view, layoutGravity, left, top, right, bottom, null);
    }

    /**
     * 追加一个View
     *
     * @param view
     * @param layoutGravity
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void appendView(@NonNull DialogWrapper.WFrameLayout view, int layoutGravity, int left, int top, int right, int bottom, DialogWrapper.OnDialogViewClickListener onListener) {
        if (view != null) {
            // 设置边缘
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(dp2px(mContext, left), dp2px(mContext, top), dp2px(mContext, right), dp2px(mContext, bottom));
            params.gravity = layoutGravity;
            view.setLayoutParams(params);

            // 设置dialog
            view.setDialog((DialogWrapper) mDialog);

            // 设置监听
            view.setOnDialogViewClickListener((dialog, v) -> {
                if (onListener != null) {
                    onListener.onClick(mDialog, v);
                }
            });

            mAddList.add(view);
        }
    }

    /**
     * 添加图片
     *
     * @param img
     * @param getViewListener
     */
    public void appendWImageView(@NonNull Drawable img, DialogWrapper.GetViewListener<DialogWrapper.WImageView> getViewListener) {
        appendWImageView(img, ImageView.ScaleType.FIT_CENTER, Gravity.CENTER, 0, 0, 0, 0, getViewListener);
    }

    /**
     * 添加图片
     *
     * @param img
     * @param scaleType
     * @param layoutGravity
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param getViewListener
     */
    public void appendWImageView(@NonNull Drawable img, ImageView.ScaleType scaleType, int layoutGravity, int left, int top, int right, int bottom, DialogWrapper.GetViewListener<DialogWrapper.WImageView> getViewListener) {
        if (img != null) {
            DialogWrapper.WImageView imageView = new DialogWrapper.WImageView(mContext);
            // 设置图片
            imageView.setImageDrawable(img);
            // 设图片显示位置
            imageView.setScaleType(scaleType);
            // 设置边缘
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(dp2px(mContext, left), dp2px(mContext, top), dp2px(mContext, right), dp2px(mContext, bottom));
            params.gravity = layoutGravity;
            imageView.setLayoutParams(params);
            // 获取当前对象
            if (getViewListener != null) {
                getViewListener.getDialogView(imageView);
            }
            // 添加到列表中去
            mAddList.add(imageView);
        }
    }


    /**
     * 添加文本
     *
     * @param textContent
     * @param textSize
     * @param textColor
     * @param getViewListener
     */
    public void appendWTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, DialogWrapper.GetViewListener<DialogWrapper.WTextView> getViewListener) {
        appendWTextView(textContent, textSize, textColor, Gravity.CENTER, 0, 0, 0, 0, getViewListener);
    }

    /**
     * 添加文本
     *
     * @param textContent
     * @param textSize
     * @param textColor
     */
    public void appendWTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, int gravity, int left, int top, int right, int bottom, DialogWrapper.GetViewListener<DialogWrapper.WTextView> getViewListener) {
        if (textContent != null && !textContent.isEmpty()) {
            DialogWrapper.WTextView textView = new DialogWrapper.WTextView(mContext);
            textView.setVisibility(View.VISIBLE);
            // 设置内容
            textView.setText(Html.fromHtml(textContent));
            // 设置字体大小
            if (textSize > 0) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }
            // 设置颜色
            textView.setTextColor(textColor);
            // 设置
            textView.setGravity(gravity);
            // 设置边缘
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(dp2px(mContext, left), dp2px(mContext, top), dp2px(mContext, right), dp2px(mContext, bottom));
            textView.setLayoutParams(params);
            // 获取当前对象
            if (getViewListener != null) {
                getViewListener.getDialogView(textView);
            }
            // 添加到列表中去
            mAddList.add(textView);
        }
    }


    /**
     * 设置顶部距离
     *
     * @param spaceMarginTop
     */
    public void setSpaceMarginTop(int spaceMarginTop) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) dialog_space_top.getLayoutParams();
        linearParams.height = dp2px(mContext, spaceMarginTop);
        dialog_space_top.setLayoutParams(linearParams);
    }


    /**
     * 设置底部距离
     *
     * @param spaceMarginBottom
     */
    public void setSpaceMarginBottom(int spaceMarginBottom) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) dialog_space_bottom.getLayoutParams();
        linearParams.height = dp2px(mContext, spaceMarginBottom);
        dialog_space_bottom.setLayoutParams(linearParams);
    }

    /**
     * 添加底部点击按钮布局
     */
    public void appendWButtonLayout(CharSequence mButtonLeftText, CharSequence mButtonRightText) {
        if (mButtonLeftText != null || mButtonRightText != null) {
            btn_layout.setVisibility(View.VISIBLE);
            if (mButtonLeftText != null && mButtonLeftText.length() > 0
                    && mButtonRightText != null && mButtonRightText.length() > 0) {
                // 两个按钮都不为空
                space_middle.setVisibility(View.VISIBLE);
                mButtonLeft.setVisibility(View.VISIBLE);
                mButtonRight.setVisibility(View.VISIBLE);
                mButtonLeft.setBackground(getDrawableBottomLeftRadius(leftBg, dp2px(mContext, cornerRadius)));
                mButtonRight.setBackground(getDrawableBottomRightRadius(rightBg, dp2px(mContext, cornerRadius)));
                setLeftButton();
                setRightButton();
            } else {
                if (mButtonLeftText != null && mButtonLeftText.length() > 0) {
                    // 左边按钮不为空
                    space_middle.setVisibility(View.GONE);
                    mButtonLeft.setVisibility(View.VISIBLE);
                    mButtonLeft.setBackground(getDrawableBottomRadius(leftBg, dp2px(mContext, cornerRadius)));
                    setLeftButton();
                }
                if (mButtonRightText != null && mButtonRightText.length() > 0) {
                    // 右边按钮不为空
                    space_middle.setVisibility(View.GONE);
                    mButtonRight.setVisibility(View.VISIBLE);
                    if (isBottomSingleBtn) {
                        //  如果是底部一个大按钮
                        mButtonRight.setBackground(getDrawableSignleRadius(rightBg, dp2px(mContext, cornerRadius)));
                    } else {
                        // 贴着底部边缘按钮
                        mButtonRight.setBackground(getDrawableBottomRadius(rightBg, dp2px(mContext, cornerRadius)));
                    }
                    setRightButton();
                }
            }
            // 修改线的颜色和宽度
            if (spaceButtonTop != 0) {

            }
            if (spaceButtonMiddle != 0) {

            }
            // 修改按钮布局距离边缘的距离
            if (bottomMarginLeft != 0 || bottomMarginTop != 0 || bottomMarginRight != 0 || bottomMarginBottom != 0) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btn_layout.getLayoutParams();
                params.setMargins(dp2px(mContext, bottomMarginLeft), dp2px(mContext, bottomMarginTop), dp2px(mContext, bottomMarginRight), dp2px(mContext, bottomMarginBottom));
                btn_layout.setLayoutParams(params);
            }
        }
    }


    /**
     * 左边按钮
     */
    public void setLeftButton() {
        if (mButtonLeftText != null && mButtonLeftText.length() > 0) {
            // 设置字体
            mButtonLeft.setText(mButtonLeftText);
            // 字体大小
            if (leftButtonSize > 0) {
                mButtonLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftButtonSize);
            }
            //  设置颜色
            mButtonLeft.setTextColor(leftButtonColor);
            // 按钮监听
            if (leftClick != null) {
//                mButtonLeftMessage = mHandler.obtainMessage(ButtonHandler.BUTTON_LEFT, leftClick);
//                mButtonLeft.setOnClickListener(mButtonHandler);
                mButtonLeft.setOnClickListener(v -> leftClick.onClick(mDialog, v));
            }
        }
    }


    /**
     * 右边按钮
     */
    public void setRightButton() {
        if (mButtonRightText != null && mButtonRightText.length() > 0) {
            // 设置字体
            mButtonRight.setText(mButtonRightText);
            // 字体大小
            if (rightButtonSize > 0) {
                mButtonRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, rightButtonSize);
            }
            //  设置颜色
            mButtonRight.setTextColor(rightButtonColor);
            // 按钮监听
            if (rightClick != null) {
//                mButtonRightMessage = mHandler.obtainMessage(ButtonHandler.BUTTON_RIGHT, rightClick);
//                mButtonRight.setOnClickListener(mButtonHandler);
                mButtonRight.setOnClickListener(v -> rightClick.onClick(mDialog, v));
            }
        }
    }


    /**
     * 设置右边一个角为圆角
     *
     * @param solidColor
     * @param radius
     * @return
     */
    public static GradientDrawable getDrawableBottomRightRadius(@ColorInt int solidColor, float radius) {
        return getDrawableBottom(solidColor, new float[]{0, 0, 0, 0, radius, radius, 0, 0});
    }

    /**
     * 设置左边一个角为圆角
     *
     * @param solidColor
     * @param radius
     * @return
     */
    public static GradientDrawable getDrawableBottomLeftRadius(@ColorInt int solidColor, float radius) {
        return getDrawableBottom(solidColor, new float[]{0, 0, 0, 0, 0, 0, radius, radius});
    }

    /**
     * 设置左右两边角 为圆角
     *
     * @param solidColor
     * @param radius
     * @return
     */
    public static GradientDrawable getDrawableBottomRadius(@ColorInt int solidColor, float radius) {
        return getDrawableBottom(solidColor, new float[]{0, 0, 0, 0, radius, radius, radius, radius});
    }

    /**
     * 设置单一四个圆角按钮
     *
     * @param solidColor
     * @param radius
     * @return
     */
    public static GradientDrawable getDrawableSignleRadius(@ColorInt int solidColor, float radius) {
        return getDrawableBottom(solidColor, new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
    }

    /**
     * 绘制圆角背景色
     *
     * @param solidColor
     * @param radius
     * @return
     */
    public static GradientDrawable getDrawableBottom(@ColorInt int solidColor, float[] radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(solidColor);
        // top-left, top-right, bottom-right, bottom-left
        drawable.setCornerRadii(radius);
        return drawable;
    }

    /**
     * 启动延迟消失程序
     */
    public void startHandleDelay() {
        if (dialogDelayMillis > 0) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG, mDialog), dialogDelayMillis);
        }
    }


    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }


}
