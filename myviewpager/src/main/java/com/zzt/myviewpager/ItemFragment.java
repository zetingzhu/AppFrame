package com.zzt.myviewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


/**
 * @author: zeting
 * @date: 2020/1/7
 *  创建fragment布局
 */
public class ItemFragment extends Fragment {
    public final static String TAG = ItemFragment.class.getSimpleName();

    private Button button;
    private String position;
    private int type;
    LinearLayout item_pager_bg;

    public static ItemFragment newFragment(String position, int type) {
        Bundle args = new Bundle();
        args.putString("position", position);
        args.putInt("type", type);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getString("position");
            type = getArguments().getInt("type", 0);
        }
        setLog("Fragment onCreate       - index:" + position);
    }


    public String getContent(){
        return position ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_viewpager, container, false);
        setLog("Fragment onCreateView   - index:" + position);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.button);
        button.setAllCaps(false);
        item_pager_bg = view.findViewById(R.id.item_pager_bg);
        if (type == 0) {
            item_pager_bg.setBackgroundColor(ContextCompat.getColor(item_pager_bg.getContext(), android.R.color.holo_green_light));
            button.setText("viewpager fragment:" + position);
        } else {
            button.setText("viewpager2 fragment:" + position);
            item_pager_bg.setBackgroundColor(ContextCompat.getColor(item_pager_bg.getContext(), android.R.color.holo_blue_bright));
        }
        setLog("Fragment onViewCreated  - index:" + position);
    }

    @Override
    public void onStart() {
        super.onStart();
        setLog("Fragment onStart        - index:" + position);
    }

    @Override
    public void onResume() {
        super.onResume();
        setLog("Fragment onResume       - index:" + position);
    }

    @Override
    public void onPause() {
        super.onPause();
        setLog("Fragment onPause        - index:" + position);
    }

    @Override
    public void onStop() {
        super.onStop();
        setLog("Fragment onStop         - index:" + position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setLog("Fragment onDestroyView  - index:" + position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setLog("Fragment onDestroy      - index:" + position);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void setButtonText(String str) {
        if (button != null) {
            button.setText(str);
        }
    }

    public void setLog(String str) {
        if (type == 0) {
            Log.d(TAG, "ViewPager   " + str);
        } else {
            Log.i(TAG, "ViewPager2  " + str);
        }
    }

}
