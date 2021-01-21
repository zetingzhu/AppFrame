package frame.zzt.com.appframe.util;

import android.view.View;

/**
 * @author: zeting
 * @date: 2020/12/29
 * 处理重复点击
 *
 *  mButton.setOnClickListener(new ClickProxy(new View.OnClickListener() {
 *             @Override
 *             public void onClick(View v) {
 *                 //to do
 *             }
 *         }));
 *
 *
 *         //提供一个静态方法
 * public class ClickFilter {
 *     public static void setFilter(View view) {
 *         try {
 *             Field field = View.class.getDeclaredField("mListenerInfo");
 *             field.setAccessible(true);
 *             Class listInfoType = field.getType();
 *             Object listinfo = field.get(view);
 *             Field onclickField = listInfoType.getField("mOnClickListener");
 *             View.OnClickListener origin = (View.OnClickListener) onclickField.get(listinfo);
 *             onclickField.set(listinfo, new ClickProxy(origin));
 *         } catch (Exception e) {
 *             e.printStackTrace();
 *         }
 *     }
 * }
 *
 *     private StateButton mStateButton;//自定义控件
 *
 *     private void initView() {
 *         ClickFilter.setFilter(mStateButton);
 *     }
 *
 */
public class RepeatClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private long lastClick = 0;
    private long times = 1000; //ms
    private IAgain mIAgain;

    public RepeatClickProxy(View.OnClickListener origin, long timems, IAgain again) {
        this.origin = origin;
        this.mIAgain = again;
        this.times = timems;
    }

    public RepeatClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClick >= times) {
            origin.onClick(v);
            lastClick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }
}
