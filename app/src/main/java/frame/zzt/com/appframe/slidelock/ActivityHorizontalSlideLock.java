package frame.zzt.com.appframe.slidelock;

import android.app.Activity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;

/**
 * Created by zeting
 * Date 19/4/23.
 */

public class ActivityHorizontalSlideLock extends Activity {


    @BindView(R.id.slideLockHorizontal)
    public SlideLockHorizontalView slideLockHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_lock_h);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        slideLockHorizontal.setIsMove(true);

    }


}
