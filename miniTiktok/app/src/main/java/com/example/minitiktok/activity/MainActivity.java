package com.example.minitiktok.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minitiktok.R;
import com.example.minitiktok.api.IMiniDouyinService;
import com.example.minitiktok.base.BaseActivity;
import com.example.minitiktok.base.BasePagerAdapter;
import com.example.minitiktok.fragment.MainFragment;
import com.example.minitiktok.utils.RxBus;
import com.example.minitiktok.video.GetVideosResponse;
import com.example.minitiktok.video.PauseVideoEvent;
import com.example.minitiktok.video.VideoClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;
    private BasePagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public static int curMainPage;
    private MainFragment mainFragment = new MainFragment();


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        viewPager = findViewById(R.id.viewpager);
        fragments.add(mainFragment);
        try {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 2);
        } catch (Exception e) {
            Log.d("TAG", "Error when opening");
            e.printStackTrace();
        }
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragments, new String[]{"",""});
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curMainPage = position;

                if (position == 0) {
                    RxBus.getDefault().post(new PauseVideoEvent(true));
                } else if (position == 1){
                    RxBus.getDefault().post(new PauseVideoEvent(false));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}