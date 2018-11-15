package com.bcm.havoc.mylibrary.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by zd on 16/5/18.
 */
public class SystemUtil {


    public static int dpToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    /**
     * 删除ArrayList中重复元素，保持顺序
     */
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

}
