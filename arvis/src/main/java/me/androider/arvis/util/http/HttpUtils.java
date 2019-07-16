package me.androider.arvis.util.http;

/**
 * 工具类-网络请求工具类
 * created by Androider on 2019/7/15 15:29
 */
public class HttpUtils {

    private static HttpUtils instance;

    private HttpUtils() {

    }

    public static HttpUtils getInstance(Class <? extends HttpUtils> clazz) {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }



}
