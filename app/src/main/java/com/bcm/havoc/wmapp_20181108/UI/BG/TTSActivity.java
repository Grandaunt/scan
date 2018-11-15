package com.bcm.havoc.wmapp_20181108.UI.BG;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bcm.havoc.wmapp_20181108.R;

public class TTSActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        button1 = (Button)findViewById(R.id.bg_rk);
        button2 = (Button)findViewById(R.id.bg_ck);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bg_rk:
                intent = new Intent(TTSActivity.this,BGInActivity.class);
                startActivity(intent);
                break;
            case R.id.bg_ck:
                intent = new Intent(TTSActivity.this,BGOutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
