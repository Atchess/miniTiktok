package com.example.minitiktok.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.example.minitiktok.activity.MyVideoActivity;
import com.example.minitiktok.activity.UploadActivity;
import com.example.minitiktok.base.BaseActivity;
import com.example.minitiktok.base.BaseFragment;
import com.example.minitiktok.R;
import com.example.minitiktok.base.BasePagerAdapter;
import com.example.minitiktok.utils.RxBus;
import com.example.minitiktok.video.PauseVideoEvent;

import java.util.ArrayList;

public class MainFragment extends BaseFragment {

    private RecommendFragment recommendFragment;
    private AllFragment allFragment;
    ViewPager viewPager;
    XTabLayout tabTitle;
    XTabLayout tabMainMenu;
    ImageView newVedio;
    Button button;

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
        newVedio = rootView.findViewById(R.id.new_vedio);
        button = rootView.findViewById(R.id.btn_myVideo);


        setFragment();
        setMainMenu();
        newVedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), MyVideoActivity.class);
                    startActivity(intent);
            }
        });
    }

    private void setFragment(){

        recommendFragment = new RecommendFragment();
        allFragment = new AllFragment();
        fragments.add(allFragment);
        fragments.add(recommendFragment);

        tabTitle.addTab(tabTitle.newTab().setText("本地"));
        tabTitle.addTab(tabTitle.newTab().setText("推荐"));

        pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), fragments, new String[] {"本地","推荐"});
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setupWithViewPager(viewPager);
        tabTitle.getTabAt(1).select();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPage = position;
                if (position == 1) {
                    //继续播放
                    RxBus.getDefault().post(new PauseVideoEvent(true));
                } else {
                    //切换到其他页面，需要暂停视频
                    RxBus.getDefault().post(new PauseVideoEvent(false));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setMainMenu() {
        tabMainMenu.addTab(tabMainMenu.newTab().setText(""));
        tabMainMenu.addTab(tabMainMenu.newTab().setText(""));
        tabMainMenu.addTab(tabMainMenu.newTab().setText(""));
        tabMainMenu.addTab(tabMainMenu.newTab().setText(""));
        tabMainMenu.addTab(tabMainMenu.newTab().setText(""));
    }
}
