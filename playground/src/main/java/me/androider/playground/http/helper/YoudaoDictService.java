package me.androider.playground.http.helper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * created by Androider on 2019/7/18 13:26
 */
public interface YoudaoDictService {

    /**
     * 通过有道词典查询
     * @param q
     * @param doctype json/xml
     * @return
     */
    @GET("suggest")
    Call<String> youdaoDictQueryGet(@Query("q") String q, @Query("doctype") String doctype);

    @FormUrlEncoded
    @POST("suggest")
    Call<String> youdaoDictQueryPost(@Field("q") String q, @Field("doctype") String doctype);

}
