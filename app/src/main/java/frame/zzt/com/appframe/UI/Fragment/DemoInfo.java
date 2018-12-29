package frame.zzt.com.appframe.UI.Fragment;

import android.app.Activity;

/**
 * Created by zeting
 * Date 18/12/26.
 */

public class DemoInfo {
    public  int title;
    public  int desc;
    public  Class<? extends Activity> demoClass;

    public DemoInfo(int title, int desc,
                    Class<? extends Activity> demoClass) {
        this.title = title;
        this.desc = desc;
        this.demoClass = demoClass;
    }


}
