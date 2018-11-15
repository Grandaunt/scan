package com.bcm.havoc.mylibrary.Utils.alioss;


import android.content.Context;

import com.bcm.havoc.mylibrary.Utils.logger.Logger;

import java.io.File;


public class OssUploadUtil {

    private static String mDomain = "";
    private static String mEndpoint = "";
    private static String mAccessKeyId = "";
    private static String mAccessKeySecret = "";
    private static String mBucket = "";
    private static String mAllEndPoint = "";//oss bucket全路径
    private static final String PreWeb = "http://";
    private static OssUploadUtil OssUploadUtil;
    private static OssService ossService;
    private static Context mContext;
    private static String mTenantName;

    public static OssUploadUtil initData(Context context, String tenantName,
                                         String domain, String endpoint, String accessKeyId, String accessKeySecret, String bucket, String allEndPoint) {
        mDomain = domain;
        mEndpoint = endpoint;
        mAccessKeyId = accessKeyId;
        mAccessKeySecret = accessKeySecret;
        mBucket = bucket;
        mAllEndPoint = allEndPoint;
        mContext = context;
        mTenantName = tenantName;


        //初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数）
        ossService = new OssService(mContext, accessKeyId, accessKeySecret, PreWeb + allEndPoint, bucket);
        //初始化OSSClient
        ossService.initOSSClient();

        if (OssUploadUtil == null)
            OssUploadUtil = new OssUploadUtil();
        return OssUploadUtil;
    }

    public void upShareImage(final String path, final OssComplete ossComplete) {
        File file = new File(path);
        final String pathStr = mTenantName + "/image/share/" + file.getName();
        Logger.w("上传路径" + pathStr);

        //开始上传，参数分别为content，上传的文件名filename，上传的文件路径filePath
        ossService.beginUpload(mContext, pathStr, path);
        //上传的进度回调
        ossService.setProgressCallback(new OssService.ProgressCallback() {
            @Override
            public void onProgressCallback(final double progress) {
                if (ossComplete != null)
                    ossComplete.UpProgress();
            }
        });
        ossService.setCompleteCallback(new OssService.CompleteCallback() {
            @Override
            public void onCompleteCallback() {
                /*最终的路径*/
                //String url = "https://" + mBucket + "." + mEndpoint + File.separator + fileName;
                String url = mDomain + pathStr;
                if (ossComplete != null)
                    ossComplete.UpComplete(url);
            }

            @Override
            public void onFailCallback() {
                if (ossComplete != null)
                    ossComplete.UpFailed();
            }
        });
    }

    public void upNewEntryImage(final String path, final OssComplete ossComplete) {
        File file = new File(path);
        final String pathStr = mTenantName + "/image/newentry/" + file.getName();
        Logger.w("上传路径" + pathStr);

        //开始上传，参数分别为content，上传的文件名filename，上传的文件路径filePath
        ossService.beginUpload(mContext, pathStr, path);
        //上传的进度回调
        ossService.setProgressCallback(new OssService.ProgressCallback() {
            @Override
            public void onProgressCallback(final double progress) {
                if (ossComplete != null)
                    ossComplete.UpProgress();
            }
        });
        ossService.setCompleteCallback(new OssService.CompleteCallback() {
            @Override
            public void onCompleteCallback() {
                /*最终的路径*/
                //String url = "https://" + mBucket + "." + mEndpoint + File.separator + fileName;
                String url = mDomain + pathStr;
                if (ossComplete != null)
                    ossComplete.UpComplete(url);
            }

            @Override
            public void onFailCallback() {
                if (ossComplete != null)
                    ossComplete.UpFailed();
            }
        });
    }

    /*上传完成后的回调*/
    public interface OssComplete {
        void UpProgress();

        void UpComplete(String url);

        void UpFailed();
    }
}
