package com.example.minitiktok.activity;

import android.content.Intent;
import android.os.CountDownTimer;

import com.example.minitiktok.R;
import com.example.minitiktok.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {

        new CountDownTimer(500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }
}