package com.bcm.havoc.mylibrary.Utils;

/**
 * Created by Admin on 2018/5/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.bcm.havoc.mylibrary.Application.MyApplication;

import java.io.File;


public class ImageUtil {
    public static final String CROP_CACHE_FILE_NAME = "icon_my.jpg";
    public static final int REQUEST_GALLERY = 0xa0;
    public static final int REQUEST_CAMERA = 0xa1;
    public static final int RE_GALLERY = 127;
    public static final int RE_CAMERA = 128;
    private static long i = 0;

    private ImageUtil() {
    }

    private static ImageUtil instance = new ImageUtil();

    public static ImageUtil getCropHelperInstance() {
        return instance;
    }

    private Uri buildUri() {
        File file = new File(MyApplication.SDPath);
        if (!file.exists())
            file.mkdirs();
        if (i == 0)
            i = System.currentTimeMillis();
        return Uri.fromFile(new File(MyApplication.SDPath))
                .buildUpon().appendPath("IM" + i + CROP_CACHE_FILE_NAME).build();
    }

    public void sethandleResultListerner(CropHandler handler, int requestCode,
                                         int resultCode, Intent data) {
        if (handler == null)
            return;
        if (resultCode == Activity.RESULT_CANCELED) {
            handler.onCropCancel();
        } else if (resultCode == Activity.RESULT_OK) {
            Bitmap photo=null;
            switch (requestCode) {
                case RE_CAMERA:
//                    if (data == null || data.getExtras() == null) {
//                        handler.onCropFailed("CropHandler's context MUST NOT be null!");
//                        return;
//                    }
//                    photo = data.getExtras().getParcelable("data");
//                    try {
//                        photo.compress(Bitmap.CompressFormat.JPEG, 30,
//                                new FileOutputStream(getCachedCropFile()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    try {
                        photo= BitmapFactory.decodeStream(handler.getContext().getContentResolver().openInputStream(buildUri()));
                    }catch (Exception E){}

                    handler.onPhotoCropped(photo, requestCode, getCachedCropFile().getAbsolutePath());
                    i=0;
                    break;
                case RE_GALLERY:
//                    if (data == null || data.getExtras() == null) {
//                        handler.onCropFailed("CropHandler's context MUST NOT be null!");
//                        return;
//                    }
//                    photo = data.getExtras().getParcelable("data");
//                    try {
//                        photo.compress(Bitmap.CompressFormat.JPEG, 30,
//                                new FileOutputStream(getCachedCropFile()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    try {
                        photo= BitmapFactory.decodeStream(handler.getContext().getContentResolver().openInputStream(buildUri()));
                    }catch (Exception E){}

                    handler.onPhotoCropped(photo, requestCode, getCachedCropFile().getAbsolutePath());
                    i=0;
                    break;
                case REQUEST_CAMERA:
                    Intent intent = buildCropIntent(buildUri());
                    if (handler.getContext() != null) {
                        handler.getContext().startActivityForResult(intent,
                                RE_CAMERA);
                    } else {
                        handler.onCropFailed("CropHandler's context MUST NOT be null!");
                    }
                    break;
                case REQUEST_GALLERY:
                    if (data == null) {
                        handler.onCropFailed("Data MUST NOT be null!");
                        return;
                    }
                    Intent intent2 = buildCropIntent(data.getData());

                    if (handler.getContext() != null) {
                        handler.getContext().startActivityForResult(intent2,
                                RE_GALLERY);
                    } else {
                        handler.onCropFailed("CropHandler's context MUST NOT be null!");
                    }
                    break;
            }
        }
    }


    public Intent buildGalleryIntent() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");

        return galleryIntent;
    }

    public Intent buildCameraIntent() {
        Intent cameraIntent = new Intent();
        cameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hasSdcard()) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, buildUri());
        }

        return cameraIntent;
    }

    private boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private Intent buildCropIntent(Uri uri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(uri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 8);
        cropIntent.putExtra("aspectY", 8);
        cropIntent.putExtra("outputX", 300);
        cropIntent.putExtra("outputY", 300);
       // cropIntent.putExtra("return-data", true);
        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        //uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, buildUri());
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        return cropIntent;
    }

    public interface CropHandler {
        void onPhotoCropped(Bitmap photo, int requesCode, String imagePath);

        void onCropCancel();

        void onCropFailed(String message);

        Activity getContext();
    }

    public File getCachedCropFile() {
        if (buildUri() == null)
            return null;
        return new File(buildUri().getPath());
    }

    /*从图片路径获取图片*/
    public static Bitmap getBitmapFromFile(File dst) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            //opts.inJustDecodeBounds = false;
            opts.inSampleSize = 2;

            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
