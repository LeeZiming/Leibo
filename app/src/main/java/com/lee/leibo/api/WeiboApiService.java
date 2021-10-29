package com.lee.leibo.api;

import model.ListData;
import model.WeiboResponse;

import io.reactivex.Observable;
import me.goldze.mvvmhabit.http.BaseResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeiboApiService {

    //获取指定微博的转发微博列表
    @GET("statuses/repost_timeline")
    Observable<BaseResponse<ListData<WeiboResponse>>> getWeiboList(@Query("access_token") String token,
                                                                   @Query("id") long id);
}
