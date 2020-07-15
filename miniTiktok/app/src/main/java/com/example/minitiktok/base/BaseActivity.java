package com.example.minitiktok.base;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;;

/**
 * create by libo
 * create on 2020-05-19
 * description activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        init();
    }

    protected abstract int setLayoutId();

    protected abstract void init();

    /**
     * Activity退出动画
     */
    protected void setExitAnimation(int animId) {
        overridePendingTransition(0, animId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
