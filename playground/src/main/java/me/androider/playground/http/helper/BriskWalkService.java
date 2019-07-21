package me.androider.playground.http.helper;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * created by Androider on 2019/7/18 16:19
 */
public interface BriskWalkService {

    /**
     * 同步本机步数, 暂时只支持iOS, 此处拿来测试上传json
     * @param access_token
     * @param model
     * @return
     */
    @POST("bwuser/syncOtherSouceWalkData.dow")
    Call<String> syncOtherSouceWalkData(@Query("access_token") String access_token,
                                        @Body SyncOtherSouceWalkDataModel model);

    /**
     * 混合文本文件上传(支持多种类型, 推荐使用), @Headers是拿来玩的, 可以不加
     * @param requestBody
     * @return
     */
    @POST("file/mobilefileupload.doy")
    @Headers({"Accept-Encoding: application/json","User-Agent: Android"})
    Call<String> mobilefileuploadWithBody(@Body RequestBody requestBody);

    /**
     * 单文件上传
     * @param file
     * @return
     */
    @Multipart
    @POST("file/mobilefileupload.doy")
    Call<String> mobilefileuploadWithPart(@Part MultipartBody.Part file);

    /**
     * 多文件上传
     * @param partList
     * @return
     */
    @Multipart
    @POST("file/mobilefileupload.doy")
    Call<String> mobilefileuploadWithPartList(@Part List<MultipartBody.Part> partList);

    /**
     * 不是很感兴趣, 有兴趣再玩
     * @param map
     * @return
     */
    @POST("file/mobilefileupload.doy")
    Call<String> mobilefileuploadWithPartMap(@PartMap Map<String, RequestBody> map);
}
