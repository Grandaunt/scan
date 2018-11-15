package com.bcm.havoc.mylibrary.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.havoc.mylibrary.Application.MyApplication;
import com.bcm.havoc.mylibrary.R;


/**
 * Created by win7 on 2017/5/2.
 */

public class ToastUtil {

    private static Toast toast;

    public static void showToast(String text) {


        if (isMainThread()) {
            if (toast == null) {
                toast = Toast.makeText(MyApplication.application, text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
            }
            toast.show();
        }
        else {
            Looper.prepare();
            if (toast == null) {
                toast = Toast.makeText(MyApplication.application, text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
            }
            toast.show();
            Looper.loop();
        }

    }

    /*判断当前是否在主线程*/
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /*特殊位置的吐司*/
    public static void showToast(Context context, String string) {

        @SuppressLint("WrongConstant") Toast toast = Toast.makeText(context, string, 0);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        TextView textView = linearLayout.findViewById(R.id.content);
        textView.setText(string);
        toast.setView(linearLayout);
        toast.setGravity(Gravity.TOP, 0, 260);
        toast.show();
    }
}
