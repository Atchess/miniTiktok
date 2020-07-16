package com.example.minitiktok.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.minitiktok.R;
import com.example.minitiktok.base.BaseRvAdapter;
import com.example.minitiktok.base.BaseRvViewHolder;
import com.example.minitiktok.video.VideoClass;
import com.example.minitiktok.view.ControllerView;
import com.example.minitiktok.view.LikeView;
import java.util.List;

/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class VideoAdapter extends BaseRvAdapter<VideoClass, VideoAdapter.VideoViewHolder> {

    public VideoAdapter(Context context, List<VideoClass> datas) {
        super(context, datas);
        Log.i("TAG","VideoAdapter");
    }

    @Override
    protected void onBindData(VideoViewHolder holder, VideoClass videoClass, int position) {
        holder.controllerView.setVideoData(videoClass);

        Glide.with(holder.ivCover.getContext()).load(Uri.parse(videoClass.getCoverRes())).into(holder.ivCover);
        Log.i("TAG","VideoAdapter");

        holder.likeView.setOnLikeListener(() -> {
            if (!videoClass.isLiked()) {  //未点赞，会有点赞效果，否则无
                holder.controllerView.like();
            }

        });
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    public class VideoViewHolder extends BaseRvViewHolder {
        LikeView likeView;
        ControllerView controllerView;
        ImageView ivCover;

        public VideoViewHolder(View itemView) {
            super(itemView);
            likeView = itemView.findViewById(R.id.likeview);
            controllerView = itemView.findViewById(R.id.controller);
            ivCover = itemView.findViewById(R.id.iv_cover);
        }
    }
}
