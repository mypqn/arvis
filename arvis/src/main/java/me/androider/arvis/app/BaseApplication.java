package me.androider.arvis.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * 基类-Application基类
 *
 * @author Androider
 * @ -12,136 +8,6 @@ import android.util.Log;
 * created by Androider on 2019/5/29 15:43
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private final String TAG = BaseApplication.class.getSimpleName();

    private boolean isShowActivityName = true;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged: " + newConfig.orientation);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory: ");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate: ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        trimMemoryLevel(level);
    }

    private void trimMemoryLevel(int level) {
        switch (level) {
            case 5:
                Log.i(TAG, "trimMemoryLevel: 5 TRIM_MEMORY_RUNNING_MODERATE");
                break;
            case 10:
                Log.i(TAG, "trimMemoryLevel: 10 TRIM_MEMORY_RUNNING_LOW");
                break;
            case 15:
                Log.i(TAG, "trimMemoryLevel: 15 TRIM_MEMORY_RUNNING_CRITICAL");
                break;
            case 20:
                Log.i(TAG, "trimMemoryLevel: 20 TRIM_MEMORY_UI_HIDDEN");
                break;
            case 40:
                Log.i(TAG, "trimMemoryLevel: 40 TRIM_MEMORY_BACKGROUND");
                break;
            case 60:
                Log.i(TAG, "trimMemoryLevel: 60 TRIM_MEMORY_MODERATE");
                break;
            case 80:
                Log.i(TAG, "trimMemoryLevel: 80 TRIM_MEMORY_COMPLETE");
                break;
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (isShowActivityName) {
            String s = activity.getLocalClassName();
            if (savedInstanceState != null) {
                s += savedInstanceState.getClass().getSimpleName();
            }
            Log.i(TAG, "onActivityCreated: " + s);
        } else {
            Log.i(TAG, "onActivityCreated: ");
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (isShowActivityName) {
            Log.i(TAG, "onActivityStarted: " + activity.getLocalClassName());
        } else {
            Log.i(TAG, "onActivityStarted: ");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (isShowActivityName) {
            Log.i(TAG, "onActivityResumed: " + activity.getLocalClassName());
        } else {
            Log.i(TAG, "onActivityResumed: ");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (isShowActivityName) {
            Log.i(TAG, "onActivityPaused: " + activity.getLocalClassName());
        } else {
            Log.i(TAG, "onActivityPaused: ");
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (isShowActivityName) {
            Log.i(TAG, "onActivityStopped: " + activity.getLocalClassName());
        } else {
            Log.i(TAG, "onActivityStopped: ");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (isShowActivityName) {
            String s = activity.getLocalClassName();
            if (outState != null) {
                s += outState.getClass().getSimpleName();
            }
            Log.i(TAG, "onActivitySaveInstanceState: " + s);
        } else {
            Log.i(TAG, "onActivitySaveInstanceState: ");
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (isShowActivityName) {
            Log.i(TAG, "onActivityDestroyed: " + activity.getLocalClassName());
        } else {
            Log.i(TAG, "onActivityDestroyed: ");
        }
    }
}