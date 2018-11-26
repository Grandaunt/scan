package com.bcm.havoc.wmapp_20181108.UI.BG;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bcm.havoc.mylibrary.Utils.JsonUtils;
import com.bcm.havoc.mylibrary.Utils.KeyBoardUtil;
import com.bcm.havoc.mylibrary.Utils.ToastUtil;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

//包裹出库
// ***快递单号不能为null
@ContentView(R.layout.activity_bgout)
public class BGOutActivity extends TTSBaseActivity implements EasyPermissions.PermissionCallbacks, QRCodeView.Delegate {
//    @ViewInject(R.id.btn_one)
//    private Button btnOne;
    @ViewInject(R.id.btn_two)
    private Button btnTwo;
    @ViewInject(R.id.btn_three)
    private Button btnThree;

    @ViewInject(R.id.rv_list)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.zbarview)
    private ZBarView mZBarView;
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final String TAG = BGInActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private int id = 0;
    //    private OrderPack orderPack;
    private MyApplication application;
    private BaseQuickAdapter adapter;
    private List<OrderPack> mDataList;
    private View top;
//    private int upDown = 2;
    // ================== 初始化参数设置开始 ==========================
    /**
     * 发布时请替换成自己申请的appId appKey 和 secretKey。注意如果需要离线合成功能,请在您申请的应用中填写包名。
     * 本demo的包名是com.baidu.tts.sample，定义在build.gradle中。
     */
    protected String appId = "14770428";

    protected String appKey = "5PfgrceECDCUcf71zjxhY0MG";

    protected String secretKey = "qo9AePqbGCuOR5FLpAYKNi1V6MDP8vBX";

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
    int delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bgin);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setHead_toolbar(true, "包裹出库", false).setDarkTheme();
        BGAQRCodeUtil.setDebug(true);
        application = (MyApplication) getApplication();
        x.view().inject(this);
        mZBarView.setDelegate(this);
        initView();
        initAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodeQRCodePermissions();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZBarView.showScanRect(); // 显示扫描框
        mZBarView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 只识别扫描框的码
        mZBarView.setType(BarcodeType.HIGH_FREQUENCY, null); // 只识别 QR_CODE
        mZBarView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
        mZBarView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别


    }

    private void initView() {
        mDataList = new ArrayList<>();
//        mDataList.add(new OrderPack(id, "订单编号", "", "", 0, 0, "", "操作人"));

//        application.getUserid()
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        top = getLayoutInflater().inflate(R.layout.header_bg_ck, (ViewGroup) mRecyclerView.getParent(), false);
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

    private void initAdapter() {
        adapter = new MyAdapter(null);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//动画效果为缩放
//        动画默认只执行一次,如果想重复执行可设置
        adapter.isFirstOnly(false);
        adapter.addHeaderView(top);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mDataList.remove(position);
                adapter.remove(position);
//                Log.d(TAG, "onItemChildClick: ");
//                Toast.makeText(ItemClickActivity.this, "onItemChildClick" + position, Toast.LENGTH_SHORT).show();

            }
        });


        mRecyclerView.setAdapter(adapter);
    }

    public class MyAdapter extends BaseQuickAdapter<OrderPack, BaseViewHolder> {
        public MyAdapter(List data) {
            super(R.layout.item_tab_scan_ck, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderPack item) {

            helper.addOnClickListener(R.id.imbtn_cancel);
//            helper.setText(R.id.item_tv_item_id, id + "");
            helper.setText(R.id.item_tv_item_express_abnormal, item.getAbnormal());
            helper.setText(R.id.item_tv_item_express_num, item.getExpressNumber());
//            helper.setText(R.id.item_tv_item_express_name, item.getOperator());

        }
    }

    //等同于@Event(value={R.id.btn_get,R.id.btn_post},type=View.OnClickListener.class)
    @Event(value = { R.id.btn_two, R.id.btn_three})
    private void getEvent(View view) {
        switch (view.getId()) {
//            case R.id.btn_one:
//                //上下行
//                if (upDown == 2) {
//                    upDown = 1;
//                    btnOne.setText("上行");
//
//                } else {
//                    upDown = 2;
//                    btnOne.setText("下行");
//                }
//                break;
            case R.id.btn_two:
                //手动输入
                ManualInput();
                break;
            case R.id.btn_three:
                //完成
//                ToastUtils.showLongToast("3");
                sendOrderPack();
                break;
        }
    }

    //手动添加快递单
    private void ManualInput() {
        inputServer = new EditText(this);
//        inputServer.setBackgroundColor(0);
        inputServer.setInputType( InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,com.bcm.havoc.mylibrary.R.style.dialog);
//        DialogActivity
        builder.setTitle("请输入快递单号").setView(inputServer)
                .setNegativeButton("取消", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        KeyBoardUtil.getInstance(BGOutActivity.this).hide();
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if(inputServer.getText().toString()!=null&&!inputServer.getText().toString().equals("")) {
                    onScanQRCodeSuccess(inputServer.getText().toString());

                }
//                ToastUtil.showToast(inputServer.getText().toString());
                KeyBoardUtil.getInstance(BGOutActivity.this).hide();
            }
        });
        builder.show();
        KeyBoardUtil.getInstance(BGOutActivity.this).show();
    }

    private void sendOrderPack() {
        id++;
        String expnum = "";
        for (int i = 0; i < mDataList.size(); i++) {
            expnum = expnum + mDataList.get(i).getExpressNumber() + "@";
        }
//        if(orderPack.getExpressNumber()==null){
//            ToastUtils.showLongToastSafe("无订单编号");
//            return;
//        }
        RequestParams params = new RequestParams(AppConfig.URL_COURIER_OUT);
//        RequestParams params = new RequestParams("http://172.16.10.242:8080/storage/interface/outStorage");
//        http://www.kzs1.cn/storage/interface/outStorageInfo?expressNumber=3967950525457@9640134029472@6640134029472&status=2&operator=%E5%88%98%E9%9B%85
//        Gson gson = new Gson();
//        String RequestStr = gson.toJson(mDataList);
//        if (mDataList == null) {
//            ToastUtil.showToast("请重新扫描");
//        }
//        Logger.i(TAG, "包裹入库RequestStr：" + RequestStr);
//        params.setAsJsonContent(true);
//        params.setBodyContent(RequestStr);
//        params.setCharset("UTF-8");
//        Logger.i(TAG, "params=" + params);
        params.addBodyParameter("expressNumber", expnum);
//      params.addBodyParameter("expressNumber","3967950525457@");
        params.addParameter("status", "2");
        params.addParameter("operator", "");
        Logger.i(params.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Logger.i("包裹出库：" + result);
                pageResults Entity = JsonUtils.getPerson(result, pageResults.class);
                Logger.e("包裹出库：" + Entity.getStatus());
                if (Entity.getMsg() != null) {
                    Logger.e("包裹出库：" + Entity.getMsg());
//                    speak(Entity.getMsg()); // 合成并播放
                }
                if (Entity.getStatus().equals("200")) {
                    Logger.i(result);
                    speak(Entity.getMsg()); // 合成并播放
//                    adapter.setNewData(null);
//                    mDataList.clear();

                } else {
                    speak("出库异常"); // 合成并播放
                    Logger.e("包裹出库：" + Entity.getData());
                    String[] error= Entity.getData().split(",");
                    Logger.e("包裹出库：" + error.length+"");
                    for(int i=0;i<error.length;i++){
                        for(int j=0;j<mDataList.size();j++){
                            if(mDataList.get(j).getExpressNumber().equals(error[i])){
                                mDataList.get(j).setAbnormal("异常");
                                continue;
                            }
                        }

                    }

                    adapter.setNewData(mDataList);
                }
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

    /*******扫描*********/
    //返回扫描结果+添加结果
    @Override
    public void onScanQRCodeSuccess(String result) {
        Logger.i(TAG, "result:" + result);
//        setTitle("扫描结果为：" + result);
        if (result != null&&result!=""&&result.length()>0) {
            for (int i = 0; i < mDataList.size(); i++) {
                if (result.equals(mDataList.get(i).getExpressNumber())) {
                    speak("重复添加"); // 合成并播放
                    ToastUtil.showToast("重复添加");
                    vibrate();
                    mZBarView.startSpotDelay(delay);//延迟delay毫秒后开始识别
                    return;
                }
            }
            mDataList.add(new OrderPack(id, result, "", "", 2, 2, "", "操作人", "", ""));
            adapter.replaceData(mDataList);
        } else {
            speak("无订单编号");
            ToastUtils.showLongToastSafe("无订单编号");
        }

        vibrate();

//        mZBarView.startSpot(); // 延迟0.1秒后开始识别
        mZBarView.startSpotDelay(delay);//延迟delay毫秒后开始识别
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
        Logger.i(TAG, "释放资源成功");
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
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

    /*******扫描*********/

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
        };
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
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
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);

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
//    public void onClick(View v) {
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
//    }


}
