package com.andrjhf.arrow.demo;

import com.andrjhf.arrow.demo.login.Logout;
import com.andrjhf.arrow.demo.login.UserLogin;
import com.andrjhf.arrow.demo.weather.Weather;
import com.andrjhf.arrow.http.ApiResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by jiahongfei on 2017/10/26.
 */

public interface ServiceApi {

    String URL_WEATHER = "101010100.html";

    @GET(URL_WEATHER)
    Observable<ApiResponse<Weather>> getWeather();


    @GET("PAHealth_v1.0.1_41_20161114_m_pahealth.apk")
    @Streaming
    Observable<ResponseBody> getApplicationApk();

    @GET("UserLogin")
    Observable<ApiResponse<UserLogin>> getUserLogin(@Query("phone") String phone,
                                                    @Query("code") String code,
                                                    @Query("blockBox") String blockBox,
                                                    @Query("extendParam") String extendParam
                                                 );

    @GET("SmsCode")
    Observable<ResponseBody> getSmsCode(String phone);

    @GET("Logout")
    Observable<ApiResponse<Logout>> logout(String cid, String phone, String userId, String currentUserToken);

}
