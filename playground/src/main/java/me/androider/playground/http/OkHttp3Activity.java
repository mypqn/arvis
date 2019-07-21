package me.androider.playground.http;

import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import me.androider.arvis.util.ToastUtils;
import me.androider.playground.app.SimpleDemoActivity;
import me.androider.playground.global.Constant;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * created by Androider on 2019/7/16 11:25
 */
public class OkHttp3Activity extends SimpleDemoActivity {

    private OkHttpClient mOkHttpClient;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOkHttpClient = new OkHttpClient();
        mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

        btnStart.setOnClickListener((v)->asyncUpload());
    }

    /**
     * 异步get
     */
    private void asyncGet() {
        mOkHttpClient = new OkHttpClient();
        Request requset = new Request.Builder()
                .url(Constant.Url.yesOrNo)
                .method("GET", null)
                .build();
        Call call = mOkHttpClient.newCall(requset);
        call.enqueue(stringCallback);
    }

    /**
     * 异步post
     */
    private void asyncPost() {
        RequestBody requestBody = new FormBody.Builder()
                .add("q", "pony")
                .add("doctype", "json")
                .build();
        Request requeset = new Request.Builder()
                .url(Constant.Url.youdaoDictSuggest)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(requeset);
        call.enqueue(stringCallback);
    }

    /**
     * 异步上传文件, 没有成功
     */
    private void asyncUpload() {
        MediaType mediaTypeJpeg = MediaType.parse("image/jpeg");
        File file = new File(getExternalFilesDir(null), "123.jpg");
        Request request = new Request.Builder()
                .url("http://walk.chd.com.cn/briskwalk/file/mobilefileupload.doy")
                .post(RequestBody.create(mediaTypeJpeg, file))
                .build();
        mOkHttpClient.newCall(request).enqueue(stringCallback);
    }

    /**
     * 同步上传文件
     * @throws IOException
     */
    private void syncUpload() throws IOException{
        MediaType mediaTypeMarkdown = MediaType.parse("text/x-markdown; charset=utf-8");
        File file = new File(getExternalFilesDir(null), "test.md");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaTypeMarkdown, file))
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        String responseStr = response.body().string();
        Log.i(TAG, "syncUpload: " + responseStr);
        if (response.isSuccessful()) {
            textView.setText(responseStr);
        } else {
            throw new IOException();
        }
    }

    /**
     * 异步下载文件
     */
    private void asyncDownload() {
        Request request = new Request.Builder()
                .url(Constant.Url.walkDownload)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.w(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                // getExternalFilesDir() /storage/emulated/0/Android/data/me.androider.playground/files
                final File file = new File(getExternalFilesDir(null),
                        Constant.Url.walkDownload.substring(Constant.Url.walkDownload.lastIndexOf("/")));
                fileOutputStream = new FileOutputStream(file);
                byte [] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                runOnUiThread(()-> ToastUtils.showShortToast(bActivity, "文件下载成功"));
            }
        });
    }

    /**
     * 异步上传多种类文件, 测试通过
     */
    private void asyncMultipartUpload() {
        MediaType mediaTypeJpeg = MediaType.parse("image/jpeg");
        File file = new File(getExternalFilesDir(null), "123.jpg");
        RequestBody fileRequestBody = RequestBody.create(mediaTypeJpeg, file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "风景")
                .addFormDataPart("image", "123.jpg", fileRequestBody)
                .build();
        Request request = new Request.Builder()
                .url("http://walk.chd.com.cn/briskwalk/file/mobilefileupload.doy")
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(stringCallback);
    }

    /**
     * 设置超时, 只能在Builder里设置
     */
    private void setTimeout() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MILLISECONDS)
                .readTimeout(10, TimeUnit.MILLISECONDS)
                .writeTimeout(10, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * 设置缓存, 只能在Builder里设置
     */
    private void setCache() {
        File cacheFile = new File(getExternalCacheDir(), "okHttp3Cache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile, cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    /**
     * 取消连接, 同 okhttp2
     */
    private void cancal() {
        Request request = new Request.Builder()
                .url(Constant.Url.yesOrNo)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(stringCallback);
        mScheduledThreadPoolExecutor.schedule(()-> call.cancel(), 100, TimeUnit.MILLISECONDS);
    }

    private void setRetry() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        Request request = new Request.Builder()
                .url(Constant.Url.yesOrNo)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(stringCallback);
    }


    private Callback stringCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            printWarn("onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                Log.i(TAG, "onResponse: " + responseStr);
                runOnUiThread(()-> textView.setText(responseStr));
            }
        }
    };
}
