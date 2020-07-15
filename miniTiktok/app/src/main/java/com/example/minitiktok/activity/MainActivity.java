package com.example.minitiktok.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.minitiktok.R;
import com.example.minitiktok.base.BaseActivity;
import com.example.minitiktok.base.BasePagerAdapter;
import com.example.minitiktok.fragment.MainFragment;

import java.util.ArrayList;

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
        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragments, new String[]{"",""});
        viewPager.setAdapter(pagerAdapter);

    }
}