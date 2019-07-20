package me.androider.arvis.app;


import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.androider.arvis.util.PermissionUtils;

/**
 * 基类-AppCompatAcitivity基类
 *
 * created by Androider on 2019/5/28 16:24
 * @author Androider
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    protected String TAG;

    protected BaseAppCompatActivity bActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        bActivity = this;
    }

    PermissionUtils bPermissionUtils;

    public void requestPermissions(@NonNull CharSequence hintMessage,
                                   @Nullable PermissionUtils.PermissionListener listener,
                                   @NonNull String... permissions) {
        if (bPermissionUtils == null) {
            bPermissionUtils = new PermissionUtils();
        }
        bPermissionUtils.requestPermissions(bActivity, hintMessage, listener, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        bPermissionUtils.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}
