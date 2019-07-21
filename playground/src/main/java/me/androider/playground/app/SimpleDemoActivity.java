package me.androider.playground.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import me.androider.arvis.app.BaseAppCompatActivity;
import me.androider.playground.R;

/**
 * created by Androider on 2019/7/17 14:12
 */
public class SimpleDemoActivity extends BaseAppCompatActivity {

    protected Button btnStart;
    protected TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);
        btnStart = findViewById(R.id.btnStart);
        textView = findViewById(R.id.textView);
    }

    protected void printInfo(Object o) {
        printInfo(String.valueOf(o));
    }

    protected void printInfo(String s) {
        Log.i(TAG, s);
        textView.append(s + "\n");
    }

    protected void printWarn(String s) {
        Log.w(TAG, s);
        textView.append(s + "\n");
    }
}
