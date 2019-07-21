package me.androider.playground.global.app;

import android.content.Intent;
import android.os.Bundle;

import me.androider.arvis.app.BaseAppCompatActivity;
import me.androider.playground.R;
import me.androider.playground.rxjava.RxJavaActivity;


public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(bActivity, RxJavaActivity.class));
    }
}
