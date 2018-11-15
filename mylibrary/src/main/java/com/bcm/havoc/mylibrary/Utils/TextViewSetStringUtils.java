package com.bcm.havoc.mylibrary.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Admin on 2018/5/30.
 */

public class TextViewSetStringUtils {
    /**
     * 设置 viewgroup 中所有 textview 字体 为 string
     *
     * @param viewGroup
     * @param string
     */
    public static void setText(ViewGroup viewGroup, String string) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup)
                setText((ViewGroup) view, string);
            else if (view instanceof TextView)
                ((TextView) view).setText(string);
        }
    }
}
