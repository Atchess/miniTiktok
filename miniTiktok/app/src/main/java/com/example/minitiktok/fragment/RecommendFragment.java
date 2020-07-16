package com.example.minitiktok.fragment;

import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.minitiktok.R;
import com.example.minitiktok.activity.MainActivity;
import com.example.minitiktok.activity.PlayListActivity;
import com.example.minitiktok.adapter.GridVideoAdapter;
import com.example.minitiktok.adapter.VideoAdapter;
import com.example.minitiktok.api.IMiniDouyinService;
import com.example.minitiktok.base.BaseFragment;
import com.example.minitiktok.utils.RxBus;
import com.example.minitiktok.video.DataCreate;
import com.example.minitiktok.video.GetVideosResponse;
import com.example.minitiktok.video.PauseVideoEvent;
import com.example.minitiktok.video.VideoClass;
import com.example.minitiktok.view.ControllerView;
import com.example.minitiktok.view.FullScreenVideoView;
import com.example.minitiktok.view.LikeView;
import com.example.minitiktok.view.viewpagerlayoutmanager.OnViewPagerListener;
import com.example.minitiktok.view.viewpagerlayoutmanager.ViewPagerLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.functions.Action1;

public class RecommendFragment extends BaseFragment {
    RecyclerView recyclerView;
    private VideoAdapter adapter;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    public static List<VideoClass> datas;
    /**
     * 当前播放视频位置
     */
    private int curPlayPos = -1;
    private FullScreenVideoView videoView;
    SwipeRefreshLayout refreshLayout;
    private ImageView ivCurCover;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void init() {
        Log.i("TAG","recommendfragment init() Start");
        recyclerView = rootView.findViewById(R.id.recyclerview);
        refreshLayout = rootView.findViewById(R.id.refreshlayout);
        //adapter = new VideoAdapter(getActivity(), new DataCreate().datas);
        //recyclerView.setAdapter(adapter);
        fetchFeed();

        videoView = new FullScreenVideoView(getActivity());

        Log.i("TAG","hghh");
        setViewPagerLayoutManager();

        setRefreshEvent();

        //监听播放或暂停事件
        RxBus.getDefault().toObservable(PauseVideoEvent.class)
                .subscribe((Action1<PauseVideoEvent>) event -> {
                    if (event.isPlayOrPause()) {
                        videoView.start();
                    } else {
                        videoView.pause();
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();

        //返回时，推荐页面可见，则继续播放视频
        if (MainActivity.curMainPage == 0 && MainFragment.curPage == 1) {
            videoView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        videoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();

        videoView.stopPlayback();
    }

    private void setViewPagerLayoutManager() {
        viewPagerLayoutManager = new ViewPagerLayoutManager(getActivity());
        recyclerView.setLayoutManager(viewPagerLayoutManager);
        recyclerView.scrollToPosition(PlayListActivity.initPos);

        viewPagerLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                playCurVideo(PlayListActivity.initPos);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (ivCurCover != null) {
                    ivCurCover.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                playCurVideo(position);
            }
        });
    }

    private void setRefreshEvent() {
        refreshLayout.setColorSchemeResources(R.color.color_link);
        refreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                refreshLayout.setRefreshing(false);
            }
        }.start());
    }

    private void playCurVideo(int position) {
        if (position == curPlayPos) {
            return;
        }

        View itemView = viewPagerLayoutManager.findViewByPosition(position);
        if (itemView == null) {
            return;
        }

        ViewGroup rootView = itemView.findViewById(R.id.rl_container);
        LikeView likeView = rootView.findViewById(R.id.likeview);
        ControllerView controllerView = rootView.findViewById(R.id.controller);
        ImageView ivPlay = rootView.findViewById(R.id.iv_play);
        ImageView ivCover = rootView.findViewById(R.id.iv_cover);
        ivPlay.setAlpha(0.4f);

        //播放暂停事件
        likeView.setOnPlayPauseListener(() -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                ivPlay.setVisibility(View.VISIBLE);
            } else {
                videoView.start();
                ivPlay.setVisibility(View.GONE);
            }
        });

        curPlayPos = position;

        //切换播放器位置
        dettachParentView(rootView);

        autoPlayVideo(curPlayPos, ivCover);
    }

    /**
     * 移除videoview父view
     */
    private void dettachParentView(ViewGroup rootView) {
        //1.添加videoview到当前需要播放的item中,添加进item之前，保证ijkVideoView没有父view
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent != null) {
            parent.removeView(videoView);
        }
        rootView.addView(videoView, 0);
    }

    /**
     * 自动播放视频
     */
    private void autoPlayVideo(int position, ImageView ivCover) {
        videoView.setVideoPath("https://media.w3.org/2010/05/sintel/trailer.mp4");
        videoView.start();
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);

            //延迟取消封面，避免加载视频黑屏
            new CountDownTimer(200, 200) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    ivCover.setVisibility(View.GONE);
                    ivCurCover = ivCover;
                }
            }.start();
        });
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
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    adapter = new VideoAdapter(getActivity(),datas);
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

