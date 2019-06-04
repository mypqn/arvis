package me.androider.arvis.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.androider.arvis.util.PermissionUtils;

/**
 * 基类-Fragment基类
 *
 * created by Androider on 2019/6/4 11:15
 * @author Androider
 */
public class BaseFragment extends Fragment {

    private static String TAG = BaseFragment.class.getSimpleName();

    protected BaseFragment bFragment;


    PermissionUtils bPermisionUtils;

    public void requestPermissions(@NonNull CharSequence hintMessage,
                                   @Nullable PermissionUtils.PermissionListener listener,
                                   @NonNull String... permissions) {
        if (bPermisionUtils == null) {
            bPermisionUtils = new PermissionUtils();
        }
        bPermisionUtils.requestPermissions(bFragment, hintMessage, listener, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        bPermisionUtils.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}
