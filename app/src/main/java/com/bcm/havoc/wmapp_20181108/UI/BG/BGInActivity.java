package com.bcm.havoc.wmapp_20181108.UI.BG;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bcm.havoc.mylibrary.Utils.JsonUtils;
import com.bcm.havoc.mylibrary.Utils.ToastUtils;
import com.bcm.havoc.mylibrary.Utils.logger.Logger;
import com.bcm.havoc.wmapp_20181108.AppConfig;
import com.bcm.havoc.wmapp_20181108.Entity.OrderPack;
import com.bcm.havoc.wmapp_20181108.Entity.pageResults;
import com.bcm.havoc.wmapp_20181108.MyApplication;
import com.bcm.havoc.wmapp_20181108.R;
import com.bcm.havoc.wmapp_20181108.UI.BG.control.InitConfig;
import com.bcm.havoc.wmapp_20181108.UI.BG.control.MySyntherizer;
import com.bcm.havoc.wmapp_20181108.UI.BG.control.NonBlockSyntherizer;
import com.bcm.havoc.wmapp_20181108.UI.BG.listener.UiMessageListener;
import com.bcm.havoc.wmapp_20181108.Util.AutoCheck;
import com.bcm.havoc.wmapp_20181108.Util.OfflineResource;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

@ContentView(R.layout.activity_bgin)
public class BGInActivity extends TTSBaseActivity implements EasyPermissions.PermissionCallbacks , QRCodeView.Delegate {
    @ViewInject(R.id.et_kd_dh)
    private TextView etKdDh;
    @ViewInject(R.id.tv_scan_reslf)
    private TextView tvScanReslf;
    @ViewInject(R.id.tv_scan_comp)
    private TextView tvScanComp;
    @ViewInject(R.id.imbtn_scan)
    private Button imbtnScan;
    @ViewInject(R.id.spinner)
    private Spinner spinner;
    @ViewInject(R.id.zbarview)
    private ZBarView mZBarView;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final String TAG = BGInActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private int id=0;
    private OrderPack orderPack;
    private MyApplication application;
    // ================== 初始化参数设置开始 ==========================

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;
    EditText inputServer;
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
    // assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;

    protected static String YYDESC = "";

    protected Handler mainHandler;
    int delay=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bgin);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setHead_toolbar(true, "包裹入库", false).setDarkTheme();
        BGAQRCodeUtil.setDebug(true);
        application = (MyApplication) getApplication();
        x.view().inject(this);
        mZBarView.setDelegate(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZBarView.showScanRect(); // 显示扫描框
        mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 只识别扫描框的码
        mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
        mZBarView.setType(BarcodeType.ONE_DIMENSION, null); // 只识别一维条码
        /**
         * 所有格式ALL,
         * 所有一维条码格式 ONE_DIMENSION,
         * 所有二维条码格式 TWO_DIMENSION,
         * 仅 QR_CODE  ONLY_QR_CODE,
         * 仅 CODE_128 ONLY_CODE_128,
         * 仅 EAN_13 ONLY_EAN_13,
         * 高频率格式，包括 QR_CODE、ISBN13、UPC_A、EAN_13、CODE_128    HIGH_FREQUENCY,
         * 自定义格式 CUSTOM
         */
        mZBarView.setType(BarcodeType.HIGH_FREQUENCY, null); // 只识别 QR_CODE
        mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
    }

    private void initView() {
        orderPack = new OrderPack(id,"","","",1,2,"","","","");
//        application.getUserid()
        spinner.setSelection(1,true);//默认选择下行
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] updown = getResources().getStringArray(R.array.updown);
                orderPack.setUpDown(i+1);
//                Toast.makeText(BGInActivity.this, "你点击的是:"+languages[pos], 2000).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mainHandler = new Handler() {
            /*
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                handle(msg);
            }

        };
        initialTts(); // 初始化TTS引擎
    }
    //返回扫描结果
    @Override
    public void onScanQRCodeSuccess(String result) {
        Logger.i(TAG, "result:" + result);
//        setTitle("扫描结果为：" + result);
        orderPack.setExpressNumber(result);
        etKdDh.setText(result);
        vibrate();
        sendOrderPack();
//        mZBarView.startSpot(); // 延迟0.1秒后开始识别
        mZBarView.startSpotDelay(delay);//延迟delay毫秒后开始识别
    }
    private void sendOrderPack() {
        id++;
        if(orderPack.getExpressNumber()==null){
            ToastUtils.showLongToastSafe("无订单编号");
            return;
        }
        RequestParams params = new RequestParams(AppConfig.URL_COURIER_IN);
//        http://www.kzs1.cn/storage/interface/enterStorage?expressNumber=73105090565685&status=1&upDown=2
//        Gson gson =new Gson();
//        String RequestStr = gson.toJson(orderPack);
//        Logger.i("包裹入库RequestStr："+RequestStr);
//        params.setAsJsonContent(true);
//        params.setBodyContent(RequestStr);
//        params.setCharset("UTF-8");
//        Logger.i("params="+params);
        params.addBodyParameter("expressNumber",orderPack.getExpressNumber());
        params.addParameter("status",orderPack.getStatus()+"");
        params.addParameter("upDown",orderPack.getUpDown()+"");
        params.addParameter("operator",orderPack.getOperator()+"");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Logger.i("包裹入库："+result);
                pageResults Entity = JsonUtils.getPerson(result, pageResults.class);
                Logger.i("包裹入库："+Entity.getStatus());
                tvScanReslf.setText(Entity.getMsg());
                speak(Entity.getMsg()); // 合成并播放

                if(Entity.getData()!=null) {
                    tvScanComp.setText(Entity.getData());
//                    speak(Entity.getData()); // 合成并播放
                }

//                if (Entity.getStatus().equals("200")) {
//                    Logger.i(result);
//                    speak(Entity.getMsg()); // 合成并播放
//                }else {
////                    Toast.makeText(BGInActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
//                }

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    //等同于@Event(value={R.id.btn_get,R.id.btn_post},type=View.OnClickListener.class)
    @Event(value = {R.id.imbtn_scan})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.imbtn_scan:
                onScanQRCodeSuccess(etKdDh.getText().toString());
                break;

        }
    }

    /*******语音*********/
    protected void initialTts() {
        LoggerProxy.printable(true); // 日志打印在logcat中
        // 设置初始化参数
        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler);

        Map<String, String> params = getParams();


        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(AppConfig.appId, AppConfig.appKey, AppConfig.secretKey, ttsMode, params, listener);

        // 如果您集成中出错，请将下面一段代码放在和demo中相同的位置，并复制InitConfig 和 AutoCheck到您的项目中
        // 上线时请删除AutoCheck的调用
        AutoCheck.getInstance(getApplicationContext()).check(initConfig, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainDebugMessage();
                        Logger.i(message); // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }

        });
        synthesizer = new NonBlockSyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
    }


    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "1");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "15");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());
        return params;
    }


    protected OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(this, voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
            Logger.i("【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }

    /**
     * speak 实际上是调用 synthesize后，获取音频流，然后播放。
     * 获取音频流的方式见SaveFileActivity及FileSaveListener
     * 需要合成的文本text的长度不能超过1024个GBK字节。
     */
    private void speak(String speakStr) {
        int result = synthesizer.speak(speakStr);
        checkResult(result, "speak");
    }


    /**
     * 切换离线发音。注意需要添加额外的判断：引擎在合成时该方法不能调用
     */
    private void loadModel(String mode) {
        offlineVoice = mode;
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        Logger.i("切换离线语音：" + offlineResource.getModelFilename());
        int result = synthesizer.loadModel(offlineResource.getModelFilename(), offlineResource.getTextFilename());
        checkResult(result, "loadModel");
    }

    private void checkResult(int result, String method) {
        if (result != 0) {
            Logger.i("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }


    /**
     * 暂停播放。仅调用speak后生效
     */
    private void pause() {
        int result = synthesizer.pause();
        checkResult(result, "pause");
    }

    /**
     * 继续播放。仅调用speak后生效，调用pause生效
     */
    private void resume() {
        int result = synthesizer.resume();
        checkResult(result, "resume");
    }

    /*
     * 停止合成引擎。即停止播放，合成，清空内部合成队列。
     */
    private void stop() {
        int result = synthesizer.stop();
        checkResult(result, "stop");
    }



    protected void handle(Message msg) {
        switch (msg.what) {
            case INIT_SUCCESS:
//                for (Button b : buttons) {
//                    b.setEnabled(true);
//                }
                msg.what = PRINT;
                break;
            default:
                break;
        }
        super.handle(msg);
    }



    /*******语音*********/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }


    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZBarView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZBarView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZBarView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Logger.e(TAG, "打开相机出错");
    }
    @Override
    protected void onStop() {
        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        mZBarView.onDestroy(); // 销毁二维码扫描控件
        synthesizer.release();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.start_preview:
//                mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//                break;
//            case R.id.stop_preview:
//                mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
//                break;
//            case R.id.start_spot:
//                mZBarView.startSpot(); // 延迟0.1秒后开始识别
//                break;
//            case R.id.stop_spot:
//                mZBarView.stopSpot(); // 停止识别
//                break;
//            case R.id.start_spot_showrect:
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.stop_spot_hiddenrect:
//                mZBarView.stopSpotAndHiddenRect(); // 停止识别，并且隐藏扫描框
//                break;
//            case R.id.show_scan_rect:
//                mZBarView.showScanRect(); // 显示扫描框
//                break;
//            case R.id.hidden_scan_rect:
//                mZBarView.hiddenScanRect(); // 隐藏扫描框
//                break;
//            case R.id.decode_scan_box_area:
//                mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
//                break;
//            case R.id.decode_full_screen_area:
//                mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(false); // 识别整个屏幕中的码
//                break;
//            case R.id.open_flashlight:
//                mZBarView.openFlashlight(); // 打开闪光灯
//                break;
//            case R.id.close_flashlight:
//                mZBarView.closeFlashlight(); // 关闭闪光灯
//                break;
//            case R.id.scan_one_dimension:
//                mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
//                mZBarView.setType(BarcodeType.ONE_DIMENSION, null); // 只识别一维条码
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.scan_two_dimension:
//                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//                mZBarView.setType(BarcodeType.TWO_DIMENSION, null); // 只识别二维条码
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.scan_qr_code:
//                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//                mZBarView.setType(BarcodeType.ONLY_QR_CODE, null); // 只识别 QR_CODE
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.scan_code128:
//                mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
//                mZBarView.setType(BarcodeType.ONLY_CODE_128, null); // 只识别 CODE_128
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.scan_ean13:
//                mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
//                mZBarView.setType(BarcodeType.ONLY_EAN_13, null); // 只识别 EAN_13
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.scan_high_frequency:
//                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//                mZBarView.setType(BarcodeType.HIGH_FREQUENCY, null); // 只识别高频率格式，包括 QR_CODE、ISBN13、UPC_A、EAN_13、CODE_128
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.scan_all:
//                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//                mZBarView.setType(BarcodeType.ALL, null); // 识别所有类型的码
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//            case R.id.scan_custom:
//                mZBarView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//
//                List<BarcodeFormat> formatList = new ArrayList<>();
//                formatList.add(BarcodeFormat.QRCODE);
//                formatList.add(BarcodeFormat.ISBN13);
//                formatList.add(BarcodeFormat.UPCA);
//                formatList.add(BarcodeFormat.EAN13);
//                formatList.add(BarcodeFormat.CODE128);
//                mZBarView.setType(BarcodeType.CUSTOM, formatList); // 自定义识别的类型
//
//                mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
//                break;
//
//            case R.id.choose_qrcde_from_gallery:
//                /*
//                从相册选取二维码图片，这里为了方便演示，使用的是
//                https://github.com/bingoogolapple/BGAPhotoPicker-Android
//                这个库来从图库中选择二维码图片，这个库不是必须的，你也可以通过自己的方式从图库中选择图片
//                 */
////                Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
////                        .cameraFileDir(null)
////                        .maxChooseCount(1)
////                        .selectedPhotos(null)
////                        .pauseOnScroll(false)
////                        .build();
////                startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
//                break;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mZBarView.showScanRect();

//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
//            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
//            mZBarView.decodeQRCode(picturePath);
//        }
    }

}
