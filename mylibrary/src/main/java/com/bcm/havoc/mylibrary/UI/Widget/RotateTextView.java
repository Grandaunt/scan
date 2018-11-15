package com.bcm.havoc.mylibrary.UI.Widget;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * @author Tyler
 * @time 2015-11-25 下午1:45:07
 */
public class RotateTextView extends TextView {


    public RotateTextView(Context context) {
        super(context);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.rotate(45, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }

}