package com.example.coordinatorlayoutsample;


import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author: zeting
 * @date: 2020/11/14
 */
public class MyScrollHeaderBehavior<T extends View> extends CoordinatorLayout.BaseSavedState {
    public MyScrollHeaderBehavior(Parcel source) {
        super(source);
    }

    public MyScrollHeaderBehavior(Parcelable superState) {
        super(superState);
    }
}
