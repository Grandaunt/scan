package com.bcm.havoc.mylibrary.UI.Widget.dragCecycleView.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bcm.havoc.mylibrary.R;
import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by zd on 2017/2/22.
 */

public class SelectImgHolder extends RecyclerView.ViewHolder {

    private LongPressListener listener;
    ImageView ivAdd;
    ImageView ivImg, ivDel;
    FrameLayout frameLayoutImgs;
    public int mPosition;
    Context mContext;

    public SelectImgHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        ivAdd = (ImageView) itemView.findViewById(R.id.ivAdd);
        ivDel = (ImageView) itemView.findViewById(R.id.ivDel);
        ivImg = (ImageView) itemView.findViewById(R.id.ivImg);
        frameLayoutImgs = (FrameLayout) itemView.findViewById(R.id.frameLayoutImgs);
    }

    public void bind(final String url, int position) {
        isShowAdd(url);
        mPosition = position;
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.delPicture(url, mPosition);
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.addPicture();
            }
        });
        frameLayoutImgs.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null)
                    listener.longPress(SelectImgHolder.this);
                return false;
            }
        });
        refreTxt(url);
    }

    void refreTxt(String url) {
        ivImg.setImageResource(android.R.color.transparent);
        File file = new File(url);
        System.out.println("-------------照片地址：" + url);
        //Glide.with(mContext).load(file).placeholder(R.mipmap.ic_delete).into(ivImg);
        if (!url.startsWith("http"))
            Glide.with(mContext).load(file).into(ivImg);
        else
            Glide.with(mContext).load(url).into(ivImg);
    }

    void isShowAdd(String txt) {
        if ("添加".equals(txt)) {
            ivImg.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
            frameLayoutImgs.setVisibility(View.GONE);
        } else {
            ivImg.setVisibility(View.VISIBLE);
            ivAdd.setVisibility(View.GONE);
            frameLayoutImgs.setVisibility(View.VISIBLE);
        }
    }

    public interface LongPressListener {
        void longPress(SelectImgHolder holder);

        void delPicture(String url, int mPosition);

        void addPicture();
    }

    public SelectImgHolder setLister(LongPressListener listener) {
        this.listener = listener;
        return this;
    }
}
