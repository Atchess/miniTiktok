package com.example.minitiktok.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.example.minitiktok.R;
import com.example.minitiktok.utils.OnVideoControllerListener;
import com.example.minitiktok.video.VideoClass;

import static android.view.animation.Animation.INFINITE;

public class ControllerView extends RelativeLayout implements View.OnClickListener {

    LottieAnimationView animationView;
    RelativeLayout rlLike;
    RelativeLayout rlRecord;
    ImageView ivFocus;
    private OnVideoControllerListener listener;
    private VideoClass videoData;

    public ControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_controller, this);
        rlLike = rootView.findViewById(R.id.rl_like);
//        ivFocus = rootView.findViewById(R.id.iv_focus);
        rlRecord = rootView.findViewById(R.id.rl_record);
        animationView = rootView.findViewById(R.id.lottie_anim);
        rlLike.setOnClickListener(this);
//        ivFocus.setOnClickListener(this);


        setRotateAnim();
    }

    public void setVideoData(VideoClass videoData) {
        this.videoData = videoData;
        animationView.setAnimation("like.json");
    }

    public void setListener(OnVideoControllerListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }

        switch (v.getId()) {

            case R.id.rl_like:
                listener.onLikeClick();
                like();
                break;
            case R.id.iv_focus:
                if (!videoData.isFocused()) {
                    videoData.setLiked(true);
                    ivFocus.setVisibility(GONE);
                }
                break;
        }
    }

    /**
     * 点赞动作
     */
    public void like() {
        if (!videoData.isLiked()) {
            //点赞
            animationView.setVisibility(VISIBLE);
            animationView.playAnimation();
            //ivLike.setTextColor(getResources().getColor(R.color.color_FF0041));
        } else {
            //取消点赞
            animationView.setVisibility(INVISIBLE);
            //ivLike.setTextColor(getResources().getColor(R.color.white));
        }

        videoData.setLiked(!videoData.isLiked());
    }

    /**
     * 循环旋转动画
     */
    private void setRotateAnim() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 359,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(INFINITE);
        rotateAnimation.setDuration(8000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rlRecord.startAnimation(rotateAnimation);
    }
}
