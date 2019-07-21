package me.androider.playground.app;

import android.content.Intent;
import android.os.Bundle;

import me.androider.arvis.app.BaseAppCompatActivity;
import me.androider.playground.R;
import me.androider.playground.http.OkHttp2Activity;
import me.androider.playground.http.OkHttp3Activity;
import me.androider.playground.http.RetrofitActivity;
import me.androider.playground.rxjava.RxJavaActivity;


public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(bActivity, RxJavaActivity.class));
    }
}
