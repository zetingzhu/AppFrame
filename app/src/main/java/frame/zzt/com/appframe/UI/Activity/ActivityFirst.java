package frame.zzt.com.appframe.UI.Activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;

/**
 * Created by allen on 18/8/8.
 */

public class ActivityFirst extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        ButterKnife.bind(this );
    }

}
