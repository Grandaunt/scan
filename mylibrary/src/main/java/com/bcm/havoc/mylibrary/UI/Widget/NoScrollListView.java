package com.bcm.havoc.mylibrary.UI.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/***
 * 自定义ListView子类，继承ListView
 * 
 * @author Administrator
 * 
 */
public class NoScrollListView extends ListView {
	/**
	 * 是否可以滑动
	 */
	public boolean noscroll = true;

	public NoScrollListView(Context context) {
		super(context);
		 
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = heightMeasureSpec;
		if (noscroll)
			expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
	

}