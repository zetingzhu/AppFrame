package frame.zzt.com.appframe.networkopt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

/**
 * Created by zeting
 * Date 19/7/23.
 */

public class ActivityNetwork extends BaseAppCompatActivity {
    private static final String TAG = ActivityNetwork.class.getSimpleName() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        NetworkManager.getInstance().init();

        NetworkManager.getInstance().registerObserver(ActivityNetwork.this);

    }

    @Network(netType = NetworkType.WIFI)
    public void network(NetworkType networkType){
        switch (networkType){
            case WIFI :
                Log.i(TAG , "当前网络为 -- wifi " ) ;
            break;
            case GPRS:
                Log.i(TAG , "当前网络为 -- GPRS " ) ;
            break;
            case NONE:
                Log.i(TAG , "当前网络为 -- 没有网络 " ) ;
            break;
            case AUTO:
                Log.i(TAG , "当前网络为 -- 自动 " ) ;
            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().unRegisterObserver(ActivityNetwork.this);
    }
}
