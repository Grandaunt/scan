package com.bcm.havoc.mylibrary.Utils;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;


/**
 * Created by Administrator on 2017/11/2 0002.
 * 实现控件的颜色渐变
 */

public class ColorGrad {
    private static int scrolly = 0;

    /**
     * 标题栏颜色渐变的实现
     * 具体是根据listview scrolly 来设置 gradview 的 argb
     * @param listView
     * @param GradView 渐变控件 一般是标题栏
     */
    public static void colorGrad(ListView listView, final View GradView) {

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentfirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;
                View firstView = view.getChildAt(0);
                if (null != firstView) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecod();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(firstVisibleItem, itemRecord);
                }
                scrolly = Math.abs(getScrollY());
              //  scrolly= RefreshLinearLayout.scrollY;
                if (scrolly < 255 ) {
//
//                    postview(GradView, Color.argb(scrolly,
//                            0xff, 0xff, 0xff));
                    postview(GradView, Color.argb(scrolly/100,
                            0xff, 0xff, 0xff));

                } else if (scrolly >= 100) {
                    postview(GradView, Color.argb(0xff, 0xff, 0xff, 0xff));
                } else {

                    scrolly = 0;
                    postview(GradView, Color.argb(00, 0x00, 0x00, 0x00));
                }

//
//                scrolly = Math.abs(getScrollY());
//                if (scrolly < 255 ) {
//
//                    postview(GradView, Color.argb(scrolly,
//                            0xff, 0xff, 0xff));
//
//
//                } else if (scrolly >= 255) {
//                    postview(GradView, Color.argb(0xff, 0xff, 0xff, 0xff));
//                } else {
//
//                    scrolly = 0;
//                    postview(GradView, Color.argb(00, 0x00, 0x00, 0x00));
//                }
            }


            private int getScrollY() {
                try {
                    int height = 0;
                    for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                        ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                        height += itemRecod.height;
                    }
                    ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                    if (null == itemRecod) {
                        itemRecod = new ItemRecod();
                    }
                    return height - itemRecod.top;
                } catch (Exception E) {
                    return 500;
                }
            }


            class ItemRecod {
                int height = 0;
                int top = 0;
            }
        });

    }

    private static void postview(final View v, final int color) {
        v.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                v.setBackgroundColor(color);
            }
        });
    }
}
