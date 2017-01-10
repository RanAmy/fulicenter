package cn.ucai.fulicenter.controller.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.view.utils.MFGT;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //  延时2秒，然后跳转到MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.startActivity(SplashActivity.this,MainActivity.class);
                MFGT.finish(SplashActivity.this);
            }
        },2000);
    }
}
