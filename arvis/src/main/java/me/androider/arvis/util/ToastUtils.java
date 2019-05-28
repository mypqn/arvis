package me.androider.arvis.util;

import android.content.Context;
import android.widget.Toast;

import me.androider.arvis.R;

/**
 * created by Androider on 2019/5/27 21:06
 */
public class ToastUtils {

    private static Toast instance;

    private static int gravity;

    public static void showDebugShortToast(Context context, String msg) {
        if (PackageManagerUtils.isDebug(context))
            showShortToast(context, String.format("%s %s", context.getString(R.string.debug_label), msg));
    }

    public static void showDebugLongToast(Context context, String msg) {
        if (PackageManagerUtils.isDebug(context))
            showLongToast(context, String.format("%s %s", context.getString(R.string.debug_label), msg));
    }

    public static void showShortToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT, gravity, 0, 0);
    }

    public static void showShortToast(Context context, int msgResId) {
        showToast(context, context.getString(msgResId), Toast.LENGTH_SHORT, gravity, 0, 0);
    }

    public static void showLongToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_LONG, gravity, 0, 0);
    }

    public static void showLongToast(Context context, int msgResId) {
        showToast(context, context.getString(msgResId), Toast.LENGTH_LONG, gravity, 0, 0);
    }

    public static void showToast(Context context, String msg , int duration ) {
        showToast(context, msg, duration , gravity, 0, 0);
    }

    public static void showToast(Context context, int msgResId , int duration ) {
        showToast(context, context.getString(msgResId), duration , gravity, 0, 0);
    }

    public static void showToast(Context context, int msgResId, int duration, int gravity, int xOffset, int yOffset) {
        showToast(context, context.getString(msgResId), duration, gravity, xOffset, yOffset);
    }

    public static void showToast(Context context, String message, int duration, int gravity, int xOffset, int yOffset) {
        if (null == message)
            throw new IllegalArgumentException("请设置要显示的文字内容");
        if (null == instance) {
            instance = Toast.makeText(context, "", duration);
        } else {
            instance.cancel();
            instance = Toast.makeText(context, "", duration);
        }
        instance.setText(message);
        instance.setDuration(duration);
        instance.setGravity(gravity, xOffset, yOffset);
        instance.show();
    }


}
