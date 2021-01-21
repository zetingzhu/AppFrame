package frame.zzt.com.appframe.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import frame.zzt.com.appframe.R;

/**
 * @author: zeting
 * @date: 2021/1/6
 */
public class MyDialogFragment extends DialogFragment {
    private Context mContext;
    private View baseView;
    private View mRootView;

    public static void showDialog(FragmentManager manager) {
        MyDialogFragment fragment = new MyDialogFragment();
        fragment.show(manager, "MyDialogFragment");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        baseView = inflater.inflate(R.layout.dialog_fragment_layout, null);
        return baseView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        final Dialog dialog = getDialog();
//        if (dialog != null) {
//            if (dialog.getWindow() != null) {
//                //设置宽高
//                dialog.getWindow().setLayout(ScreenUtils.getScreenWidth(getContext()) * 90 / 192, WindowManager.LayoutParams.WRAP_CONTENT);
//            }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(baseView);
    }

    private void initViews(View view) {
        //设置dialog的高
        mRootView = view.findViewById(R.id.ll_layout);
//        ViewGroup.LayoutParams params = mRootView.getLayoutParams();
//        params.height = ScreenUtils.getScreenHeight(getContext()) * 5 / 7;
//        mRootView.setLayoutParams(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        int i = transaction.add(this, tag).commitAllowingStateLoss();
        return i;
//        return super.show(transaction ,tag);
    }
}

