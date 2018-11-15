package com.bcm.havoc.mylibrary.UI.Widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.SimpleViewSwitcher;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;

/**
 * Created by Admin on 2018/10/13.
 */

public class MXRLoadingMoreFooter extends LinearLayout {
    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;


    public MXRLoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public MXRLoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressCon = new SimpleViewSwitcher(getContext());
        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);

        addView(progressCon);
        mText = new TextView(getContext());
        mText.setText("正在加载...");

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins( (int)getResources().getDimension(com.jcodecraeer.xrecyclerview.R.dimen.textandiconmargin),0,0,0 );

        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void setProgressStyle(int style) {
        if(style == ProgressStyle.SysProgress){
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        }else{
            AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setText(getContext().getText(com.jcodecraeer.xrecyclerview.R.string.listview_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText(getContext().getText(com.jcodecraeer.xrecyclerview.R.string.listview_loading));
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText("\n"+"-没有更多了-"+"\n");
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

}
