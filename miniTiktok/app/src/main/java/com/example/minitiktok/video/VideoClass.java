package com.example.minitiktok.video;

import com.google.gson.annotations.SerializedName;

public class VideoClass {
    @SerializedName("student_id") public String studentId;
    @SerializedName("user_name") public String userName;
    @SerializedName("image_url") public String imageUrl;
    @SerializedName("video_url") public String videoUrl;
    @SerializedName("image_w") public int imageWidth;
    @SerializedName("image_h") public int imageHeight;

    private boolean isLiked;
    private boolean isFocused;
    private int likeCount;

    public boolean isLiked() {
        return isLiked;
    }
    public int getLikeCount() {
        return likeCount;
    }
    public String getUserName() {return userName;}
    public boolean isFocused() {
        return isFocused;
    }
    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getVideoRes() {
        return videoUrl;
    }

    public void setVideoRes(String videoRes) {
        this.videoUrl = videoRes;
    }

    public String getCoverRes() { return imageUrl; }

    public void setCoverRes(String coverRes) { this.imageUrl = coverRes; }

    public String getStudentId() {return studentId;}

    public void setStudentId(String id) { this.studentId = id;}

    public void setUserName(String userName) { this.userName = userName;}

    public String getContent() {return userName;}


}
