package com.example.minitiktok.fragment;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.minitiktok.R;
import com.example.minitiktok.adapter.GridVideoAdapter;
import com.example.minitiktok.adapter.VideoAdapter;
import com.example.minitiktok.api.IMiniDouyinService;
import com.example.minitiktok.base.BaseFragment;
import com.example.minitiktok.video.DataCreate;
import com.example.minitiktok.video.GetVideosResponse;
import com.example.minitiktok.video.VideoClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by libo
 * create on 2020-05-19
 * description 附近的人fragment
 */
public class AllFragment extends BaseFragment {
    RecyclerView recyclerView;
    private GridVideoAdapter adapter;
    public static List<VideoClass> datas;

    SwipeRefreshLayout refreshLayout;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    protected void init() {

        Log.i("TAG","allfragment init() Start");
        recyclerView = rootView.findViewById(R.id.recyclerview);
        refreshLayout = rootView.findViewById(R.id.refreshlayout);


        fetchFeed();

        refreshLayout.setColorSchemeResources(R.color.color_link);
        refreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IMiniDouyinService.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);
                miniDouyinService.getVideos().enqueue(new Callback<GetVideosResponse>() {
                    @Override
                    public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
                        if (response.body() != null && response.body().videos != null) {
                            datas.clear();
                            datas = response.body().videos;
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            adapter = new GridVideoAdapter(getActivity(),datas);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<GetVideosResponse> call, Throwable throwable) {
                        Log.i("TAG","fail");
                    }
                });
            }

            @Override
            public void onFinish() {
                refreshLayout.setRefreshing(false);
            }
        }.start());
    }

    public void fetchFeed() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IMiniDouyinService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);
        miniDouyinService.getVideos().enqueue(new Callback<GetVideosResponse>() {
            @Override
            public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
                List<VideoClass> Videos = new ArrayList<>();
                if (response.body() != null && response.body().videos != null) {
                    datas = response.body().videos;
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

                    adapter = new GridVideoAdapter(getActivity(),datas);
                    recyclerView.setAdapter(adapter);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<GetVideosResponse> call, Throwable throwable) {
                Log.i("TAG","fail");
            }
        });
    }

}