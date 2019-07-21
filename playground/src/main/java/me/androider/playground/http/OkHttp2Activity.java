package me.androider.playground.http;

import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import me.androider.playground.global.app.SimpleDemoActivity;
import me.androider.playground.global.Constant;

/**
 * created by Androider on 2019/7/16 11:25
 */
public class OkHttp2Activity extends SimpleDemoActivity {

    private OkHttpClient mOkHttpClient;

    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOkHttpClient = new OkHttpClient();
        mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

//        asyncGet();

        mScheduledThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    syncGet();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        asyncPost();

        btnStart.setOnClickListener((v)-> asyncGetWithCache());
    }

    /**
     * 异步get请求, 该请求不在主线程, 在返回的时候需要手动切换到主线程
     */
    public void asyncGet() {
        Request request = new Request.Builder()
                .url(Constant.Url.yesOrNo)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(stringCallback);
    }

    /**
     * 同步get, 该请求在主线程, 需要手动切换到非主线程
     */
    public String syncGet() throws IOException{
        Request request = new Request.Builder()
                .url(Constant.Url.yesOrNo)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("syncGet failed: " + response);
        }
    }

    /**
     * 异步post
     */
    public void asyncPost() {
        RequestBody body = new FormEncodingBuilder()
                .add("q", "apple")
                .add("doctype", "json")
                .build();
        Request request = new Request.Builder()
                .url(Constant.Url.youdaoDictSuggest)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(stringCallback);
    }

    /**
     * 设置缓存
     */
    private void setCache() {
        File externalCache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(externalCache, cacheSize);
        mOkHttpClient.setCache(cache);
    }

    /**
     * 带缓存的异步get请求
     */
    private void asyncGetWithCache() {
        setCache();
        Request request = new Request.Builder()
                .url(Constant.Url.yesOrNo)
//                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i(TAG, "onFailure: " + request);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str = "";
                if (null != response.cacheResponse()) {
                    str = response.cacheResponse().toString();
                    Log.i(TAG, "onResponse: cache: " + str);
                } else {
                    str = response.networkResponse().toString();
                    Log.i(TAG, "onResponse: network: " + str);
                }
                final String responseStr = str;
                Log.i(TAG, "onResponse: " + responseStr);
                runOnUiThread(()->textView.setText(responseStr));
            }
        });
    }

    /**
     * 设置超时
     */
    private void setTimeout() {
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
    }

    /**
     * 取消网络请求
     */
    private void cancel() {
        Request request = new Request.Builder()
                .url(Constant.Url.yesOrNo)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(stringCallback);

        mScheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                if (call.isExecuted() && ! call.isCanceled()) {
                    call.cancel();
                }
            }
        }, 100, TimeUnit.MILLISECONDS);
    }


    private Callback stringCallback = new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
            printWarn("onFailure: " + e.getMessage());
        }

        @Override
        public void onResponse(Response response) throws IOException {
            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                Log.i(TAG, "onResponse: " + responseStr);
                runOnUiThread(()-> textView.setText(responseStr));
            }
        }
    };
}
