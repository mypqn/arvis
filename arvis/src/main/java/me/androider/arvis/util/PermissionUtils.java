package me.androider.arvis.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import me.androider.arvis.dict.RequestCode;

/**
 * 工具类-权限工具类
 *
 * created by Androider on 2019/5/28 15:36
 * @author Androider
 */
public class PermissionUtils {

    private PermissionListener mListener;


    /**
     * 请求权限
     *
     * @param object        传入的对象, 可能为Activity或者Fragment
     * @param hintMessage   请求权限提示信息
     * @param listener      请求权限的回调接口
     * @param permissions   请求的权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull final Object object,
                                   @NonNull CharSequence hintMessage,
                                   @Nullable PermissionListener listener,
                                   @NonNull final String... permissions) {
        if (listener != null) {
            this.mListener = listener;
        }
        if (!checkPermissions(object, permissions)) {
            // TODO: 2019/6/2
            new AlertDialog.Builder(getActivity(object)).setMessage(hintMessage)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executeRequestPermission(object, permissions);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();
        }
    }

    /**
     * 检查权限, 只要有一个未授权都返回false
     *
     * @param object        传入的对象, 可能为Activity或者Fragment
     * @param permissions   请求的权限
     * @return
     */
    public boolean checkPermissions(@NonNull Object object, @NonNull String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            boolean flag = ContextCompat.checkSelfPermission(getActivity(object), permission) ==
                    PackageManager.PERMISSION_GRANTED;
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void executeRequestPermission(@NonNull Object object, @NonNull String... permissions) {
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, permissions,
                    RequestCode.REQUEST_PERMISSION);
        } else if (object instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) object).requestPermissions(permissions,
                    RequestCode.REQUEST_PERMISSION);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(permissions,
                    RequestCode.REQUEST_PERMISSION);
        }
    }

    /**
     * 处理权限请求后的操作
     *
     * @param requestCode  请求编码
     * @param permissions  请求的权限
     * @param grantResults 授权结果
     */
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCode.REQUEST_PERMISSION) {
            int grantResultFlag = 0;
            for (int grantResult : grantResults) {
                grantResultFlag |= grantResult;
            }
            if (grantResultFlag == PackageManager.PERMISSION_GRANTED) {
                if (mListener != null) {
                    mListener.onPermissionsGranted(requestCode, permissions);
                }
            } else {
                if (mListener != null) {
                    mListener.onPermissionsDenied(requestCode, permissions);
                }
            }
        }
    }

    /**
     * 获取Activity对象
     *
     * @return  返回一个Activity对象
     */
    private Activity getActivity(@NonNull Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }


    public interface PermissionListener {

        /**
         * 当获取到授权
         *
         * @param requestCode   请求编码
         * @param permissions   请求的权限
         */
        void onPermissionsGranted(int requestCode, String[] permissions);

        /**
         * 当请求权限被拒绝
         *
         * @param requestCode   请求编码
         * @param permissions   请求的权限
         */
        void onPermissionsDenied(int requestCode, String[] permissions);
    }
}
