package com.bcm.havoc.mylibrary.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类提供在linealayout 中以一定格式添加控件的方法
 * 方法基本一样有待封装
 * @author Administrator
 * 
 */
public class AddViewUtils {
	/**
	 * 注意linearlayout 必须线布局
	 * 
	 * @param l
	 *            父控件
	 * @param vlist
	 *            添加子控件列表
	 * @param onenumber
	 *            每行添加控件个数
	 * @return 返回该方法中创建的控件列表 便于删除
	 */
	public static List<View> AddView(Context context, LinearLayout l,
                                     List<View> vlist, int onenumber, int view_width, int view_height) {
		List<View> addviewinutils = new ArrayList<View>();
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// 子分类布局
		LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lps.weight = 1;
		lps.setMargins(0, 5, 0, 0);
		lps.width = view_width;
		lps.height = view_height;

		l.removeAllViews();
		for (int i = 0; vlist.size() % onenumber == 0 ? i < vlist.size()
				/ onenumber : i <= vlist.size() / onenumber; i++) {

			LinearLayout lin = new LinearLayout(context);
			lin.setOrientation(LinearLayout.HORIZONTAL);
			addviewinutils.add(lin);
			l.addView(lin, lp);
			for (int j = 0; j < onenumber; j++) {
				if (vlist.size() > (i * onenumber + j)) {
					if (vlist.get(i * onenumber + j).getParent() != null)
						((ViewGroup) vlist.get(i * onenumber + j).getParent())
								.removeView(vlist.get(i * onenumber + j));
					lin.addView(vlist.get(i * onenumber + j), lps);
				} else
					lin.addView(new View(context), lps);

			}

		}
		//System.out.println("vlist" + vlist.size());
		return addviewinutils;

	}

	/**
	 * 注意linearlayout 必须线布局
	 * 
	 * @param l
	 *            父控件
	 * @param vlist
	 *            添加子控件列表
	 * @param onenumber
	 *            每行添加控件个数
	 * @return 返回该方法中创建的控件列表 便于删除
	 */
	public static List<View> AddView(Context context, LinearLayout l,
                                     List<View> vlist, int onenumber) {
		List<View> addviewinutils = new ArrayList<View>();
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp.weight = 1;
		// 子分类布局
		LayoutParams lps = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		lps.setMargins(5, 10, 5, 10);

		l.removeAllViews();
		for (int i = 0; vlist.size() % onenumber == 0 ? i < vlist.size()
				/ onenumber : i <= vlist.size() / onenumber; i++) {

			LinearLayout lin = new LinearLayout(context);
			lin.setOrientation(LinearLayout.HORIZONTAL);
			addviewinutils.add(lin);
			l.addView(lin, lp);
			for (int j = 0; j < onenumber; j++) {
				if (vlist.size() > (i * onenumber + j)) {
					if (vlist.get(i * onenumber + j).getParent() != null)
						((ViewGroup) vlist.get(i * onenumber + j).getParent())
								.removeView(vlist.get(i * onenumber + j));
					lin.addView(vlist.get(i * onenumber + j), lp);
					vlist.get(i * onenumber + j).setPadding(5, 2, 5, 2);
					//lin.addView(new View(context),lp);
				} else
					//lin.addView(new View(context),lp);
					lin.addView(new TextView(context), lp);

			}

		}
		System.out.println("vlist" + vlist.size());
		return addviewinutils;

	}
	
 
}
