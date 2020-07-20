package frame.zzt.com.appframe.touchanim;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * 滑动动画
 * Created by zeting
 * Date 18/12/7.
 */

public class ActivityTouchAnim extends BaseAppCompatActivity {

    @BindView(R.id.fl_move_view)
    FrameLayout fl_move_view ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_anim );
        ButterKnife.bind(this) ;
    }
}
