package com.example.minitiktok.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(setLayoutId(), container, false);

        init();
        return rootView;
    }

    protected abstract int setLayoutId();

    protected abstract void init();
}