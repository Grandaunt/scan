/**
 * Program  : SetImage.java
 * Author   : yangsen
 * Create   : 2013-4-23 下午3:30:30
 *
 * Copyright 2010 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

package com.bcm.havoc.mylibrary.Utils;

import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 实现回调接口 image图片加载特效
 * 
 * @author
 * @version
 * 
 */
public class SetImage {
	private ImageView iView;

	// 这里进行要放置图片的ImageView 的初始化
	public SetImage(ImageView imageView) {
		this.iView = imageView;
	}

	// 回调方法，当方法被调用的时候，直接进行图片的加载

	public void imageLoaded(Bitmap imageDrawable) {
		// 设置动画效果
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1f);
		ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 加入动画
		animationSet.setDuration(1 * 100);
		animationSet.addAnimation(alphaAnimation);// 设置渐隐
		animationSet.addAnimation(scaleAnimation);// 设置伸缩
		// animationSet.addAnimation(rotateAnimation);//设置旋转
		// 设置停留在动画最后的状态
		animationSet.setFillAfter(true);
		// 为ImageView加载动画
		iView.startAnimation(animationSet);
		iView.setImageBitmap(imageDrawable);
		iView.setScaleType(ScaleType.FIT_XY);

	}

}
