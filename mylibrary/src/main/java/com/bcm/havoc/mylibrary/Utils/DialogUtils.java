package com.bcm.havoc.mylibrary.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * 自定义dialog管理类
 */

public class DialogUtils extends Dialog {

    private Context context;
    private boolean cancelTouchout;
    private View view;
    private int gravity;

    private DialogUtils(Builder builder) {
        super(builder.context);
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
        gravity = builder.gravity;
    }

    private DialogUtils(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
        gravity = builder.gravity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = gravity;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
    }

    public static final class Builder {
        private Context context;
        private boolean cancelTouchout;
        private View view;
        private TextView textView, leftButton, rightButton, TitleView;
        private int resStyle = -1;
        private int gravity = Gravity.CENTER;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null, false);
            return this;
        }

        public Builder view(View resView) {
            view = resView;
            return this;
        }

        public Builder SetLeftVisable(boolean show) {
            if (show)
                leftButton.setVisibility(View.VISIBLE);
            else
                leftButton.setVisibility(View.GONE);
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setTitle(String text, int id) {
            TitleView = (TextView) view.findViewById(id);
            TitleView.setText(text);
            TitleView.setVisibility(View.VISIBLE);
            return this;
        }

        public Builder settext(String text, int id) {
            textView = (TextView) view.findViewById(id);
            textView.setText(text);
            return this;
        }

        public Builder setLeftButton(String text, int id) {
            leftButton = (TextView) view.findViewById(id);
            leftButton.setText(text);
            return this;
        }

        /*yqq*/
        public Builder setLeftButton(String text, int id, int texttcolor) {
            leftButton = (TextView) view.findViewById(id);
            leftButton.setText(text);
            leftButton.setTextColor(texttcolor);
            return this;
        }
        /*yqq*/
        public Builder setRightButton(String text, int id, int texttcolor) {
            rightButton = (TextView) view.findViewById(id);
            if (rightButton != null) {
                rightButton.setText(text);
                rightButton.setTextColor(texttcolor);
            }
            return this;
        }
        /*yqq*/
        public Builder setRightButtonYel(String text, int id, int texttcolor, int bgcolor) {
            rightButton = (TextView) view.findViewById(id);
            if (rightButton != null) {
                rightButton.setText(text);
                rightButton.setBackgroundResource(bgcolor);
                rightButton.setTextColor(texttcolor);
            }
            return this;
        }

        public Builder setRightButton(String text, int id) {
            rightButton = (TextView) view.findViewById(id);
            if (rightButton != null)
                rightButton.setText(text);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }


        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
            if (view.findViewById(viewRes) != null)
                view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public DialogUtils build() {
            if (resStyle != -1) {
                return new DialogUtils(this, resStyle);
            } else {
                return new DialogUtils(this);
            }
        }


        @NonNull
        protected NumberFormat getNumberFormat() {
            //动态适配,让布局在各种分辨率中都能完美展示效果
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            return numberFormat;
        }

    }
}
