package frame.zzt.com.appframe.ui.Fragment;

import android.app.Activity;

/**
 * Created by zeting
 * Date 18/12/26.
 */

public class DemoInfo {
    public int title;
    public int desc;
    public Class<? extends Activity> demoClass;
    public String arouter;// 路由地址
    public int clickIndex = -1;

    public DemoInfo(int title, int desc, int clickIndex) {
        this.title = title;
        this.desc = desc;
        this.clickIndex = clickIndex;
    }

    public DemoInfo(int title, int desc, String arouter) {
        this.title = title;
        this.desc = desc;
        this.arouter = arouter;
    }

    public DemoInfo(int title, int desc,
                    Class<? extends Activity> demoClass) {
        this.title = title;
        this.desc = desc;
        this.demoClass = demoClass;
    }


}
