package me.androider.arvis.util;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * 工具类-包管理工具类
 *
 * created by Androider on 2019/5/27 21:09
 * @author Androider
 */
public class PackageManagerUtils {

    private static final String TAG = PackageManagerUtils.class.getSimpleName();

    private Context mContext;

    private static PackageManagerUtils instance;

    private PackageManager mPackageManager;

    private PackageManagerUtils(Context context) {
        this.mContext = context;
        mPackageManager = context.getPackageManager();
    }

    public static PackageManagerUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (PackageManagerUtils.class) {
                if (instance == null) {
                    instance = new PackageManagerUtils(context);
                }
            }
        }
        return instance;
    }


    /**
     * 获取包信息
     * @return  返回Package的信息
     */
    public PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = mPackageManager.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 获取应用名
     * @return  返回应用的名称
     */
    public String getApplicatoinLabel() {
        String label = "";
        try {
            ApplicationInfo ai = mPackageManager.getApplicationInfo(mContext.getPackageName(), 0);
            label = mPackageManager.getApplicationLabel(ai).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return label;
    }

    /**
     * 判断当前package中是否已经声明了activity
     * @param activityName  Activity名
     * @return  boolean
     */
    public boolean isHasActivity(String activityName) {
        if (TextUtils.isEmpty(activityName)) {
            new IllegalArgumentException("activityName不能为空");
        }
        ActivityInfo [] ais = getActivities();
        for (ActivityInfo ai : ais) {
            if (ai.name.equals(activityName)) {
                return true;
            }
        }
        return false;
    }

    public ActivityInfo[] getActivities() {
        try {
            return mPackageManager.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是调试状态
     * @return boolean
     */
    public boolean isDebug() {
        try {
            ApplicationInfo ai = mContext.getApplicationInfo();
            return (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isDebug(Context context) {
        return PackageManagerUtils.getInstance(context).isDebug();
    }

    /**
     * 获取应用内标meta-data
     * @param key           键名
     * @param defaultValue  默认值
     * @return
     */
    public Object getApplicationMetaData(String key, Object defaultValue) {
        try {
            ApplicationInfo ai = mPackageManager.getApplicationInfo(mContext.getPackageName(),
                    PackageManager.GET_META_DATA);
            Object result = ai.metaData.get(key);
            return ClassUtils.getTypeValue(result);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
