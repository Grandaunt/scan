package com.bcm.havoc.mylibrary.Utils;


import android.app.Activity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bcm.havoc.mylibrary.Application.AppManager;


public class DoubleClickExit {

	/**
	 * Activity onKeyDown事件
	 * */
	private static long exitTime;
	public static boolean onKeyDown(Activity activity, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
			{
				Toast.makeText(activity.getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				activity.finish();
				AppManager.getAppManager().appExit(activity);
				System.exit(0);
			}

			return true;
		}
		return false;
	}
}
