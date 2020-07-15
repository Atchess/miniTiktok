package com.example.minitiktok.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.example.minitiktok.base.BaseActivity;
import com.example.minitiktok.base.BaseFragment;
import com.example.minitiktok.R;
import com.example.minitiktok.base.BasePagerAdapter;

import java.util.ArrayList;

public class MainFragment extends BaseFragment {

    ViewPager viewPager;
    XTabLayout tabTitle;
    XTabLayout tabMainMenu;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private BasePagerAdapter pagerAdapter;

    public static int curPage = 1;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        //rootView = getView();
        viewPager = rootView.findViewById(R.id.viewpager);
        tabTitle = rootView.findViewById(R.id.tab_title);
        tabMainMenu = rootView.findViewById(R.id.tab_mainmenu);
        setFragment();
        setMainMenu();
    }

    private void setFragment(){
        tabTitle.addTab(tabTitle.newTab().setText("本地"));
        tabTitle.addTab(tabTitle.newTab().setText("推荐"));

        pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), fragments, new String[] {"本地","推荐"});
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setupWithViewPager(viewPager);
        //tabTitle.getTabAt(1).select();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setMainMenu() {
        tabMainMenu.addTab(tabMainMenu.newTab().setText("首页"));
        tabMainMenu.addTab(tabMainMenu.newTab().setText("好友"));
        tabMainMenu.addTab(tabMainMenu.newTab().setText(""));
        tabMainMenu.addTab(tabMainMenu.newTab().setText("消息"));
        tabMainMenu.addTab(tabMainMenu.newTab().setText("我"));
    }
}
