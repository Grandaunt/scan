package com.bcm.havoc.mylibrary.Utils;


import android.content.Context;

import java.io.File;


public class OssUploadUtil {

    private static String endpoint = "";
    private static String accessKeyId = "";
    private static String accessKeySecret = "";
    private static String bucket = "";
    private static String allEndPoint = "";//oss bucket全路径
    private static String domain="";
    private static final String PreWeb = "http://";
    private static OssUploadUtil OssUploadUtil;
    private static OssService ossService;
    private static Context mcontext;
    public static OssUploadUtil initData(Context context, String domain, String endpoint, String accessKeyId, String accessKeySecret, String bucket, String allEndPoint) {
        OssUploadUtil.domain=domain;
        OssUploadUtil.endpoint = endpoint;
        OssUploadUtil.accessKeyId = accessKeyId;
        OssUploadUtil.accessKeySecret = accessKeySecret;
        OssUploadUtil.bucket = bucket;
        OssUploadUtil.allEndPoint = allEndPoint;
        mcontext=context;
        //初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数）
        ossService = new OssService(mcontext, accessKeyId, accessKeySecret, PreWeb + allEndPoint, bucket);
        //初始化OSSClient
        ossService.initOSSClient();

        if (OssUploadUtil == null)
            OssUploadUtil = new OssUploadUtil();
        return OssUploadUtil;
    }
    public void upImage(String TenantName, final String path, final OssComplete ossComplete) {
        upImage(TenantName,path,null,ossComplete);
    }
    private String currentPath;
    public void upImage(String TenantName, final String path, final String type, final OssComplete ossComplete) {
        File file = new File(path);
        if(type!=null){
            if(type.equals("头像")){
                currentPath = TenantName+"/image/user-avatar/"+file.getName();
            }else if(type.equals("意见反馈")){
                currentPath = TenantName+"/image/user-feedbacks/"+file.getName();
            }
        }else {
            currentPath = TenantName+"/image/"+file.getName();
        }

        //开始上传，参数分别为content，上传的文件名filename，上传的文件路径filePath
        ossService.beginupload(mcontext, currentPath, path);
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
                // String url = "https://" + bucket + "." + endpoint + File.separator + currentPath;
                String url = domain+ currentPath;
                if (ossComplete != null)
                    ossComplete.UpComplete(url);
            }
        });
    }
    /*上传完成后的回调*/
    public interface OssComplete {
        void UpProgress();

        void UpComplete(String url);
    }
}
