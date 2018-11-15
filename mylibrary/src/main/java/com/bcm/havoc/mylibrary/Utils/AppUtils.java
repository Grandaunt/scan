//package com.bcm.havoc.mylibrary.Utils;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.widget.TextView;
//
//
///**
// * @author yqq
// * @date 2018/9/19 0019 16:40
// * Description:This is AppUtils
// */
//public class AppUtils {
//    /**
//     * 判断TextView限定行数展开按钮是否显示
//     * */
//    public static boolean isShowGetMore(Context context, TextView textView, String str, int txtsize, int maxlines, int marginleft, int marginright) {
//        // 获得字体的宽度，sp转px的方法，网上很多，txtsize为textview中所设定的textSize属性值
//        int txtWidth = DensityUtil.dip2px(context, txtsize);
//        // 获得屏幕的宽度
//        int winWidth = ((AppBaseActivity)context).getWin_width();
//        // 获得textView控件的宽度，marginleft和marginright为xml中所设定marginleft 和 marginright的值
//        int viewWidth = winWidth
//                - DensityUtil.dip2px(context, marginleft) - DensityUtil.dip2px(context, marginright);
//        // 获得单行最多显示字数
//        int maxWords = viewWidth / txtWidth;
//        // 计算字符串长度，
//        int stringLen = str.length();
//        // 字符串长度除以单行最多显示字数为行数
//        int lines = 0;
//        if(!str.contains("\n")) {
//            lines = stringLen / maxWords;
//            if(stringLen % maxWords > 0){
//                lines = lines + 1;
//            }
//        }else{
//            String[] Strs = str.split("\n");
//            String lastStr = Strs[Strs.length - 1];
//            int lastLen = lastStr.length();
//            int lastLines = lastLen / maxWords;
//            if(lastLen % maxWords > 0){
//                lines = Strs.length + lastLines;
//            }else {
//                lines = Strs.length + lastLines - 1;
//            }
//
//        }
//
//        if (lines > maxlines) {
//            // 如果大于指定行数，则直接返回
//            return true;
//        }
//        return false;
//    }
//
//    public static Drawable getDrawable(Context context, int res) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            return context.getResources().getDrawable(res, context.getTheme());
//        } else {
//            return context.getResources().getDrawable(res);
//        }
//    }
//
//
//}
