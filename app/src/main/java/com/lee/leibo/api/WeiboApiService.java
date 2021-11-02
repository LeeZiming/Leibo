package com.lee.leibo.api;

import io.reactivex.Observable;

import com.lee.leibo.model.WeiboResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeiboApiService {

    //获取指定微博的转发微博列表
    @GET("statuses/home_timeline.json")
    Observable<WeiboResponse> getWeiboList(@Query("access_token") String token,
                                                         @Query("id") long id);
}
