package com.example.minitiktok.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.minitiktok.R;
import com.example.minitiktok.activity.PlayListActivity;
import com.example.minitiktok.base.BaseRvAdapter;
import com.example.minitiktok.base.BaseRvViewHolder;
import com.example.minitiktok.video.VideoClass;
import com.bumptech.glide.Glide;

import java.util.List;

public class GridVideoAdapter extends BaseRvAdapter<VideoClass, GridVideoAdapter.GridVideoViewHolder> {

    public GridVideoAdapter(Context context, List<VideoClass> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(GridVideoViewHolder holder, VideoClass videoClass, int position) {
        Glide.with(holder.ivCover.getContext()).load(Uri.parse(videoClass.getCoverRes())).into(holder.ivCover);
        Log.i("TAG","ic:"+videoClass.getCoverRes());
        holder.tvContent.setText(videoClass.getContent());

        holder.itemView.setOnClickListener(v -> {
            PlayListActivity.initPos = position;
            context.startActivity(new Intent(context, PlayListActivity.class));
        });
    }

    @NonNull
    @Override
    public GridVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gridvideo, parent, false);
        return new GridVideoViewHolder(view);
    }

    public class GridVideoViewHolder extends BaseRvViewHolder {
        ImageView ivCover;
        TextView tvContent;

        public GridVideoViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}