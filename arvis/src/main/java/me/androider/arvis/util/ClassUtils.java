package me.androider.arvis.util;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * 工具类-类工具类
 *
 * created by Androider on 2019/5/28 15:18
 * @author Androider
 */
public class ClassUtils {

    private static final String TAG = ClassUtils.class.getSimpleName();

    public static Object getTypeValue(Object object) {
        if (object == null) {
            return null;
        }
        String s = object.toString();
        if (object.equals(String.class)) {
            Log.d(TAG, "getTypeValue: String " + s);
            return s;
        } else if (object.equals(Boolean.class) || object.equals(boolean.class)) {
            Boolean b = Boolean.parseBoolean(s);
            Log.d(TAG, "getTypeValue: boolean " + b);
            return b;
        } else if (object.equals(Integer.class) || object.equals(int.class)) {
            Integer i = Integer.parseInt(s);
            Log.d(TAG, "getTypeValue: int " + i);
            return i;
        } else if (object.equals(Long.class) || object.equals(long.class)) {
            Long l = Long.parseLong(s);
            Log.d(TAG, "getTypeValue: long " + l);
            return l;
        } else if (object.equals(Double.class) || object.equals(double.class)) {
            Double d = Double.parseDouble(s);
            Log.d(TAG, "getTypeValue: double " + d);
            return d;
        } else if (object.equals(Short.class) || object.equals(short.class)) {
            Short sh = Short.parseShort(s);
            Log.d(TAG, "getTypeValue: short " + sh);
            return sh;
        } else if (object.equals(List.class)) {
            Log.d(TAG, "getTypeValue: List " + object);
        } else if (object.equals(Map.class)) {
            Log.d(TAG, "getTypeValue: Map " + object);
        }
        return object;
    }

    /**
     * 根据传入的类型将object转换成该类型的对象
     * @param clazz     要转换的类型
     * @param object    要转换的对象
     * @return
     */
    private static <T> T getObjectType(@NonNull Class<T> clazz, Object object) {
        return clazz.cast(object);
    }

    /**
     * 将object转换成Android上下文对象, 如果不是上下文对象则返回
     * @param contextObject
     * @return              返回一个对象
     */
    private static Object getContextObjectType(@NonNull Object contextObject) {
        if (contextObject == null) {
            throw new IllegalArgumentException("object should not null!");
        }
        if (contextObject instanceof Activity) {
            return (Activity)contextObject;
        }
        if (contextObject instanceof android.support.v4.app.Fragment) {
            return (android.support.v4.app.Fragment)contextObject;
        }
        if (contextObject instanceof android.app.Fragment) {
            return (android.app.Fragment)contextObject;
        }
        if (contextObject instanceof Application) {
            return (Application)contextObject;
        }
        return contextObject;
    }
}
