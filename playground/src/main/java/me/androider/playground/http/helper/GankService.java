package me.androider.playground.http.helper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * created by Androider on 2019/7/18 15:41
 */
public interface GankService {

    /**
     * 获取最新一天的干货
     * @return
     */
    @GET("today")
    Call<String> today();

    /**
     * 获取闲读的主分类
     * @return
     */
    @GET("xiandu/categories")
    Call<String> xianduCategories();

    /**
     * 获取闲读的子分类
     * @param en_name wow 科技资讯
     *                apps 趣味软件/游戏
     *                imrich 装备党
     *                android 不解释
     *                diediedie 创业新闻
     *                thinking 独立思想
     *                iOS 同样不解释
     *                teamblog 团队博客
     * @return
     */
    @GET("xiandu/category/{en_name}")
    Call<String> xianduCategory(@Path("en_name") String en_name);



}
