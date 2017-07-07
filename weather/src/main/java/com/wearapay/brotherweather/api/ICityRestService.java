package com.wearapay.brotherweather.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lyz54 on 2017/6/28.
 */

public interface ICityRestService {
    @GET("support/Detail.aspx")
    Observable<ResponseBody> getCityInfo(@Query("id") String id);
}
