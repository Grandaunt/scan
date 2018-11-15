package com.bcm.havoc.mylibrary.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class httpImage extends Thread {
    String url;
    View imageview;
    Bitmap mbitmap;

    public httpImage(String url, View imageview) {
        super();
        this.url = url;
        this.imageview = imageview;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        try {

            if (url != null) {
                URLConnection urlconnection = new URL(url).openConnection();
                mbitmap = BitmapFactory.decodeStream(urlconnection
                        .getInputStream());
                imageview.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if(imageview instanceof ImageView)
                            ((ImageView)imageview).setImageBitmap(mbitmap);
                        else
                        {
                            imageview.setBackgroundDrawable(new BitmapDrawable(mbitmap));
                        }
                        SystemPrintln.out("httpurl=" + url);
                    }
                });
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
