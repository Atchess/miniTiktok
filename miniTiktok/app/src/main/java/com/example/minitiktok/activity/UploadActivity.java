package com.example.minitiktok.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.minitiktok.AddVideo;
import com.example.minitiktok.R;
import com.example.minitiktok.api.IMiniDouyinService;
import com.example.minitiktok.utils.ResourceUtils;
import com.example.minitiktok.video.PostVideoResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    private static final int TAKE_VIDEO = 3;
    private static final int TAKE_IMAGE = 4;
    private static final String TAG = "UploadActivity";
    public Uri mSelectedImage;
    private Uri mSelectedVideo;
    public Button mBtn;
    private Button mBtnRefresh;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMiniDouyinService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mBtn = findViewById(R.id.btn_upload);
        findViewById(R.id.btn_upload).setOnClickListener(this);
        findViewById(R.id.btn_upload_video).setOnClickListener(this);
        findViewById(R.id.btn_upload_photo).setOnClickListener(this);
        findViewById(R.id.btn_take_video).setOnClickListener(this);
        findViewById(R.id.btn_take_photo).setOnClickListener(this);
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE);
    }

    public void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO);
    }
    private String[] mPermissionsArrays = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    private final static int REQUEST_PERMISSION = 123;

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_upload_video:
                chooseVideo();
                break;
            case R.id.btn_upload_photo:
                chooseImage();
                break;
            case R.id.btn_take_video:
            case R.id.btn_take_photo:
                if (!checkPermissionAllGranted(mPermissionsArrays)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(mPermissionsArrays, REQUEST_PERMISSION);
                    }
                }else {
                    Intent it1 = new Intent(this, AddVideo.class);
                    if(view.getId()==R.id.btn_take_photo)
                    startActivityForResult(it1, TAKE_IMAGE);
                    else startActivityForResult(it1,TAKE_VIDEO);
                }
                break;
            case R.id.btn_upload:
                postVideo();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult() called with: requestCode = ["
                + requestCode
                + "], resultCode = ["
                + resultCode
                + "], data = ["
                + data
                + "]");

        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == PICK_IMAGE) {
                mSelectedImage = data.getData();
                Log.d(TAG, "selectedImage = " + mSelectedImage);
            } else if (requestCode == PICK_VIDEO) {
                mSelectedVideo = data.getData();
                Log.d(TAG, "mSelectedVideo = " + mSelectedVideo);
            }
            else if (requestCode == TAKE_IMAGE) {
                mSelectedImage = Uri.parse(data.getStringExtra("Url"));
                Log.d(TAG, "selectedImage = " + mSelectedImage);
            } else if (requestCode == TAKE_VIDEO) {
                mSelectedVideo = Uri.parse(data.getStringExtra("Url"));
                Log.d(TAG, "mSelectedVideo = " + mSelectedVideo);
            }
        }
    }

    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        File f;
        if (uri.toString().charAt(0) != '/')
            f = new File(ResourceUtils.getRealPath(UploadActivity.this, uri));
        else
            f =new File(uri.toString());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }

    private void postVideo() {
        if (mSelectedImage==null || mSelectedVideo==null){
            Toast.makeText(UploadActivity.this, "需要选择数据", Toast.LENGTH_SHORT).show();
            return ;
        }
        mBtn.setText("POSTING...");
        mBtn.setEnabled(false);
        MultipartBody.Part coverImagePart = getMultipartFromUri("cover_image", mSelectedImage);
        MultipartBody.Part videoPart = getMultipartFromUri("video", mSelectedVideo);
        Log.i("TAG",mSelectedImage.toString() + ";" + mSelectedVideo.toString());
        //@TODO 4下面的id和名字替换成自己的
        miniDouyinService.postVideo("18888916233", "名字", coverImagePart, videoPart).enqueue(
                new Callback<PostVideoResponse>() {
                    @Override
                    public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                        Log.i("TAG","success");
                        if (response.body() != null) {
                            Toast.makeText(UploadActivity.this, response.body().toString(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                        mBtn.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<PostVideoResponse> call, Throwable throwable) {
                        Log.i("TAG","fail");
                        mBtn.setText(R.string.post_it);
                        mBtn.setEnabled(true);
                        Toast.makeText(UploadActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        Log.i("TAG","finish");
    }
    private boolean checkPermissionAllGranted(String[] permissions) {
        // 6.0以下不需要
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {

                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
}