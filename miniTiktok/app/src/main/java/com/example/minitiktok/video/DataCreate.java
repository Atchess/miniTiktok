package com.example.minitiktok.video;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minitiktok.R;
import com.example.minitiktok.activity.MainActivity;
import com.example.minitiktok.api.IMiniDouyinService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DataCreate {
    public static ArrayList<VideoClass> datas = new ArrayList<>();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMiniDouyinService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);

    public DataCreate(){
        initData();
    }

    public void initData() {
        fetchFeed();
    }

    public void fetchFeed() {
        miniDouyinService.getVideos().enqueue(new Callback<GetVideosResponse>() {
            @Override
            public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
                List<VideoClass> Videos = new ArrayList<>();
                Videos.clear();
                datas.clear();
                if (response.body() != null && response.body().videos != null) {
                    Videos = response.body().videos;
                    //@TODO  5服务端没有做去重，拿到列表后，可以在端侧根据自己的id，做列表筛选。
                    for (VideoClass video : Videos){
                        //Log.i("student_id",video.studentId);
                        //if (video.studentId.equals("18888916233"))
                            datas.add(video);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetVideosResponse> call, Throwable throwable) {
                Log.i("TAG","fail");
            }
        });
    }

}