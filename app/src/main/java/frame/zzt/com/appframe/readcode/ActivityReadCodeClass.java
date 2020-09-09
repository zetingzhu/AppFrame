package frame.zzt.com.appframe.readcode;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;
import frame.zzt.com.appframe.widget.MyRecyclerView;

/**
 * Created by allen on 18/11/9.
 */

public class ActivityReadCodeClass extends BaseAppCompatActivity {
    private final static String TAG = ActivityReadCodeClass.class.getSimpleName();

    @BindView(R.id.rv_observable)
    MyRecyclerView rv_observable;


    private List<MyRecyclerView.MyRecycleListItem> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_read_code_class);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(0, "HashMap和Hashtable"));


        rv_observable.setMyAdapter(mList, new MyRecyclerView.MyRecycleOnClick() {
            @Override
            public void onClickListener(int position) {
                Log.i(TAG, "点击数据：" + position);
                switch (position) {
                    case 0:
                        testHash();
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
            }
        });
    }

    public void testHash() {
        HashMap<String, String> mHashMap = new HashMap();
        mHashMap.put("m1", "111");
        mHashMap.get("m1");
        mHashMap.hashCode();

        Hashtable mHashtable = new Hashtable();
        mHashtable.put("t1", "1");
    }

}
