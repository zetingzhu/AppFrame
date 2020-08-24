package com.example.grouprecycle;

import android.graphics.Rect;

/**
 * @author: zeting
 * @date: 2020/8/19
 * StickyHeader 信息接口
 */
public interface IStickyHeaderDecoration {

	Rect getStickyHeaderRect();

	int getStickyHeaderPosition();

}
