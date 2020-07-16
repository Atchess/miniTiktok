package com.example.minitiktok.activity;

import com.example.minitiktok.R;
import com.example.minitiktok.base.BaseActivity;
import com.example.minitiktok.fragment.RecommendFragment;

public class PlayListActivity extends BaseActivity {
    public static int initPos;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_play_list;
    }

    @Override
    protected void init() {
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, new RecommendFragment()).commit();
    }
}
