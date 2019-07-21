package me.androider.playground.global;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Androider on 2019/7/16 14:23
 */
public abstract class Constant {

    public interface Url {

        String gank     = "http://gank.io/api/";

        /**
         * gank 获取闲读的主分类
         */
        String gankXianduCategories = "http://gank.io/api/xiandu/categories";

        /**
         * 是, 还是否, 支持get
         */
        String yesOrNo = "https://yesno.wtf/api/";

        /**
         * 随机一篇古诗词, 支持get/post, 但返回值不同, 可以无参
         */
        String gushici = "https://api.gushi.ci/";

        /**
         * 有道词典
         */
        String youdaoDict = "http://dict.youdao.com/";

        /**
         * 有道翻译, 支持get/post
         * 参数:  q: 关键字
         *        doctype: json 返回格式
         */
        String youdaoDictSuggest = "http://dict.youdao.com/suggest";

        /**
         * 今天是什么日子, 支持get/post
         * 参数:  key:    7bc8ff86168092de65576a6166bfc47b
         *        v:  1.0
         *        month:    9
         *        day:  18
         */
        String today = "http://api.juheapi.com/japi/toh";

        /**
         * 魅族天气, 支持get/post
         * 参数:  cityIds: 101190101 南京
         */
        String meizuWeather = "http://aider.meizu.com/app/weather/listWeather";

        String walkDownload     = "http://walk.chd.com.cn/download/walk-.apk";

        String BRISK_WALK       = "http://walk.chd.com.cn/briskwalk/";
    }

    private static List<String> someTextList = new ArrayList<>();

    public static List<String> getSomeTextList() {
        if (someTextList.size() == 0) {
            someTextList.add("Android");
            someTextList.add("iOS");
            someTextList.add("Html5");
        }
        return someTextList;
    }


}
