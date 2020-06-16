package frame.zzt.com.appframe.mvvmbind.util;

/**
 * 这种方法可以直接使用的布局种，用来展示显示信息
 * 使用
 *     <data>
 *
 *         <import type="frame.zzt.com.appframe.mvvmbind.util.TextUtil" />
 *
 *     </data>
 *
 *            <TextView
 *                 android:layout_width="wrap_content"
 *                 android:layout_height="wrap_content"
 *                 android:text="@{ TextUtil.showClickCount( vmData.clickCount) }" />
 *
 */
public class TextUtil {
    public static String showClickCount(int count) {
        return "点击的数量2：" + count;
    }
}
