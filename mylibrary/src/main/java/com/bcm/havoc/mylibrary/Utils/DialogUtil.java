package com.bcm.havoc.mylibrary.Utils;//package com.bcm.havoc.basice.Utils;
//
//
//import android.app.Activity;
//
//import com.century21cn.kkbl.WeChatShare.widget.bottom_menu.BottomMenuFragment;
//import com.century21cn.kkbl.WeChatShare.widget.bottom_menu.MenuItem;
//
//
///**
// * @author by Administrator on 2018/4/8.
// * @Email: renyangyang@coffee-ease.com
// * @Description:
// */
//
//public class DialogUtil {
//
//
//    public static void showShareDialog(final Activity context, BottomMenuFragment.OnItemClickListener listener){
//        new BottomMenuFragment(context)
//                .setTitle("分享到")
//                .addMenuItems(new MenuItem("微信好友"))
//                .addMenuItems(new MenuItem("微信朋友圈"))
//                .setOnItemClickListener(listener)
//                .show();
//    }
//
//    public static void showPhotoDialog(final Activity context, BottomMenuFragment.OnItemClickListener listener){
//        new BottomMenuFragment(context)
//                .addMenuItems(new MenuItem("拍照"))
//                .addMenuItems(new MenuItem("从相册选择"))
//                .setOnItemClickListener(listener)
//                .show();
//    }
//
//    public static void showMyShareDialog(final Activity context, BottomMenuFragment.OnItemClickListener listener){
//        new BottomMenuFragment(context)
//                .addMenuItems(new MenuItem("买卖"))
//                .addMenuItems(new MenuItem("租赁"))
//                .addMenuItems(new MenuItem("新房"))
//                .addMenuItems(new MenuItem("海外"))
//                .setOnItemClickListener(listener)
//                .show();
//    }
//
//}
