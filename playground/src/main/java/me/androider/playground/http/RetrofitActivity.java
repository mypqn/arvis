package me.androider.playground.http;

import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import me.androider.playground.app.SimpleDemoActivity;
import me.androider.playground.global.Constant;
import me.androider.playground.http.helper.BriskWalkService;
import me.androider.playground.http.helper.GankService;
import me.androider.playground.http.helper.SyncOtherSouceWalkDataModel;
import me.androider.playground.http.helper.YoudaoDictService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * created by Androider on 2019/7/16 11:25
 */
public class RetrofitActivity extends SimpleDemoActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnStart.setOnClickListener((v)-> uploadWithPartList());
    }

    private void get() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Url.youdaoDict)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YoudaoDictService mYoudaoDictService = retrofit.create(YoudaoDictService.class);
        Call<String> call = mYoudaoDictService.youdaoDictQueryGet("apple", "json");
        call.enqueue(stringCallback);
    }

    private void post() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Url.youdaoDict)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        YoudaoDictService service = retrofit.create(YoudaoDictService.class);
        service.youdaoDictQueryPost("pony", "json").enqueue(stringCallback);
    }

    /**
     * 使用@Path
     */
    private void usePath() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(Constant.Url.gank)
                .build();
        GankService service = retrofit.create(GankService.class);
        service.xianduCategory("android").enqueue(stringCallback);
    }

    private void useField() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Url.youdaoDict)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        YoudaoDictService service = retrofit.create(YoudaoDictService.class);
        service.youdaoDictQueryPost("post", "json").enqueue(stringCallback);
    }

    /**
     * 上传json, 使用body
     */
    private void useBody() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Url.BRISK_WALK)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BriskWalkService service = retrofit.create(BriskWalkService.class);
        SyncOtherSouceWalkDataModel model = new SyncOtherSouceWalkDataModel.Builder()
                .add(new SyncOtherSouceWalkDataModel.WalkDataModel()
                        .addDeviceId("1")
                        .addWalk_calories("1")
                        .addWalk_distance("1")
                        .addWalk_step("1")
                        .addWalk_date("2020-01-01"))
                .add(new SyncOtherSouceWalkDataModel.WalkDataModel()
                        .addDeviceId("1")
                        .addWalk_calories("1")
                        .addWalk_distance("1")
                        .addWalk_step("1")
                        .addWalk_date("2020-02-01"))
                .build();
        service.syncOtherSouceWalkData("d6e0e652-fc33-4b67-9220-9a0e9589d20e", model)
                .enqueue(stringCallback);
    }

    /**
     * 单文件上传, 已测试
     * 重点: 要使用MultipartBody.Part.createFormData
     */
    private void uploadWithPart() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(Constant.Url.BRISK_WALK)
                .build();
        BriskWalkService service = retrofit.create(BriskWalkService.class);
        MediaType mediaTypeJpeg =  MediaType.parse("image/jpeg");
        File file = new File(getExternalFilesDir(null), "123.jpg");
        RequestBody fileBody = RequestBody.create(mediaTypeJpeg, file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", "123.jpg", fileBody);
        service.mobilefileuploadWithPart(filePart).enqueue(stringCallback);
    }

    /**
     * 多文件上传, 已测试
     * 重点: 要使用MultipartBody.Part.createFormData
     */
    private void uploadWithPartList() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(Constant.Url.BRISK_WALK)
                .build();
        BriskWalkService service = retrofit.create(BriskWalkService.class);
        MediaType mediaTypeJpeg =  MediaType.parse("image/jpeg");
        File file = new File(getExternalFilesDir(null), "123.jpg");
        RequestBody fileBody = RequestBody.create(mediaTypeJpeg, file);
        MultipartBody.Part filePart1 = MultipartBody.Part.createFormData("image", "123.jpg", fileBody);
        MultipartBody.Part filePart2 = MultipartBody.Part.createFormData("image", "456.jpg", fileBody);
        List<MultipartBody.Part> partList = new ArrayList<>();
        partList.add(filePart1);
        partList.add(filePart2);
        service.mobilefileuploadWithPartList(partList).enqueue(stringCallback);
    }

    /**
     * 混合文本文件上传, 已测试
     * setType(MultipartBody.FORM) 加和不加都能上传, 但推荐加上
     */
    private void uploadWithBody() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(Constant.Url.BRISK_WALK)
                .build();
        BriskWalkService service = retrofit.create(BriskWalkService.class);
        MediaType mediaTypeJpeg =  MediaType.parse("image/jpeg");
        File file = new File(getExternalFilesDir(null), "123.jpg");
        RequestBody fileBody = RequestBody.create(mediaTypeJpeg, file);
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "value")
                .addFormDataPart("image", "123.jpg", fileBody)
                .addFormDataPart("image", "456.jpg", fileBody)
                .build();
        service.mobilefileuploadWithBody(body).enqueue(stringCallback);
    }

    private Callback<String> stringCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            printInfo("onResponse: " + response.body());
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            printWarn("onFailure: " + t.getMessage());
        }
    };
}
