package frame.zzt.com.appframe.rxjava;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by allen on 18/8/29.
 */

public class ActivityRxJavaUse extends BaseAppCompatActivity implements RxView {

    private String TAG = "ActivityRxJavaUse";

    @BindView(R.id.recycleview_use)
    public RecyclerView mRecyclerView;

    private List<MyListItem> mList;
    AdapterRecycle mAdapterRecycle;
    MyOnClick mOnClick;

    protected RxUsePersenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rx_use_java);
        ButterKnife.bind(this);

        // 申请需要使用到的权限
        myRequestPermissions();

        mPresenter = new RxUsePersenter(this , this.getBaseContext() );

        mList = new ArrayList<>();
        mList.add(new MyListItem(0, "RxJava 使用，安装应用apk "));


        mAdapterRecycle = new AdapterRecycle();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapterRecycle);
        mOnClick = new MyOnClick() {
            @Override
            public void onClickListener(int position) {
                Log.i(TAG, "点击的是：" + position);
                switch (position) {
                    case 0:
                        mPresenter.downloadApkFile() ;
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:


                        break;
                    case 4:

                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                    case 21:
                        break;
                    case 22:
                        break;
                    case 23:
                        break;
                    case 24:
                        break;
                    case 25:
                        break;

                }
            }
        };
    }


    /**
     * 申请用户权限
     */
    public void myRequestPermissions(){
        PermissionListener permission = new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> grantedPermissions) {
                // 权限申请成功回调。

                // 这里的requestCode就是申请时设置的requestCode。
                // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
                if(requestCode == 200) {
                    // TODO ...
                    Log.i(TAG, "申请权限 成功" );
                }
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
                // 权限申请失败回调。
                if(requestCode == 200) {
                    // TODO ...
                    Log.i(TAG, "申请权限 失败" );
                }
            }
        };
        RationaleListener rationale = new RationaleListener() {
            @Override
            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                Log.i(TAG, "申请被拒绝过 requestCode:" + requestCode );
//                AndPermission.rationaleDialog(getApplicationContext() , rationale).show();
            }
        };
        // 在其它任何地方：
        AndPermission.with(ActivityRxJavaUse.this)
                .requestCode(200)
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE ,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .callback(permission)
                .rationale(rationale)
                .start();


    }


    public class AdapterRecycle extends RecyclerView.Adapter<DateHolder> {

        @Override
        public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
            DateHolder viewHolder = new DateHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DateHolder holder, final int position) {
            holder.mTextview.setText(mList.get(position).getItemId() + " : " + mList.get(position).getItemValue());
            holder.mTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClick.onClickListener(mList.get(position).getItemId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public class DateHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView mTextview;

        public DateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MyOnClick {
        void onClickListener(int position);
    }

    public class MyListItem {
        public int itemId;
        public String itemValue;

        public MyListItem(int itemId, String itemValue) {
            this.itemId = itemId;
            this.itemValue = itemValue;
        }

        public int getItemId() {
            return itemId;
        }

        public String getItemValue() {
            return itemValue;
        }
    }

}
