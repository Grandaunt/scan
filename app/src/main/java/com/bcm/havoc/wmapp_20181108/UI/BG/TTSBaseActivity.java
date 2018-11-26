package com.bcm.havoc.wmapp_20181108.UI.BG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.bcm.havoc.mylibrary.UI.BaseActivity;


/**
 * Created by fujiayi on 2017/9/13.
 * <p>
 * 此类 底层UI实现 无SDK相关逻辑
 */

public class TTSBaseActivity extends BaseActivity implements MainHandlerConstant {
//    protected Button mSpeak;
//    protected Button mPause;
//    protected Button mResume;
//    protected Button mStop;
//    protected Button mSynthesize;
//    protected Button mBatchSpeak;
//    protected Button mLoadModel;
//    protected Button[] buttons;
//    protected Button mHelp;
//    protected EditText mInput;
//    protected TextView mShowText;
    protected Handler mainHandler;
    private static final String TAG = "MainActivity";

    /*
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_synth);
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
//        initialView(); // 初始化UI

    }


//    private void initialView() {
//        mSpeak = (Button) this.findViewById(R.id.speak);
//        mPause = (Button) this.findViewById(R.id.pause);
//        mResume = (Button) this.findViewById(R.id.resume);
//        mStop = (Button) this.findViewById(R.id.stop);
//        mSynthesize = (Button) this.findViewById(R.id.synthesize);
//        mBatchSpeak = (Button) this.findViewById(R.id.batchSpeak);
//        mLoadModel = (Button) this.findViewById(R.id.loadModel);
//        buttons = new Button[]{
//                mSpeak, mPause, mResume, mStop, mSynthesize, mBatchSpeak, mLoadModel
//        };
//        mHelp = (Button) this.findViewById(R.id.help);
//        mInput = (EditText) this.findViewById(R.id.input);
//        mShowText = (TextView) this.findViewById(R.id.showText);
//        mShowText.setMovementMethod(new ScrollingMovementMethod());
//
//    }

    protected void handle(Message msg) {
        int what = msg.what;
        switch (what) {
            case PRINT:
                print(msg);
                break;
            case UI_CHANGE_INPUT_TEXT_SELECTION:
//                if (msg.arg1 <= mInput.getText().length()) {
//                    mInput.setSelection(0, msg.arg1);
//                }
                break;
            case UI_CHANGE_SYNTHES_TEXT_SELECTION:
//                SpannableString colorfulText = new SpannableString(mInput.getText().toString());
//                if (msg.arg1 <= colorfulText.toString().length()) {
//                    colorfulText.setSpan(new ForegroundColorSpan(Color.GRAY), 0, msg.arg1, Spannable
//                            .SPAN_EXCLUSIVE_EXCLUSIVE);
////                    mInput.setText(colorfulText);
//                }
                break;
            default:
                break;
        }
    }

    protected void toPrint(String str) {
        Message msg = Message.obtain();
        msg.obj = str;
        mainHandler.sendMessage(msg);
    }

    private void print(Message msg) {
        String message = (String) msg.obj;
        if (message != null) {
            scrollLog(message);
        }
    }

    private void scrollLog(String message) {
        Spannable colorMessage = new SpannableString(message + "\n");
        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mShowText.append(colorMessage);
//        Layout layout = mShowText.getLayout();
//        if (layout != null) {
//            int scrollAmount = layout.getLineTop(mShowText.getLineCount()) - mShowText.getHeight();
//            if (scrollAmount > 0) {
//                mShowText.scrollTo(0, scrollAmount + mShowText.getCompoundPaddingBottom());
//            } else {
//                mShowText.scrollTo(0, 0);
//            }
//        }
    }


}
