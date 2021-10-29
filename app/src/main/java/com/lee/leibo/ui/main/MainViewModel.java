package com.lee.leibo.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.lee.leibo.api.WeiboApiService;
import com.lee.leibo.net.LeiboRequest;
import com.lee.leibo.net.RetrofitClient;
import com.lee.leibo.net.TokenManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.http.BaseResponse;
import model.ListData;
import model.WeiboResponse;

public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }


    public void reqDefaultShippingAddress() {
        LeiboRequest.builder()
                .setLifecycleProvider(getLifecycleProvider())
                .setObservable(
                        RetrofitClient
                                .getInstance()
                                .create(WeiboApiService.class)
                                .getWeiboList(TokenManager.getAccessToken(),Long.parseLong(TokenManager.getUid()))
                )
                .setObserver(new Observer<BaseResponse<ListData<WeiboResponse>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponse<ListData<WeiboResponse>> response) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                })
                .build(true);
    }

}
