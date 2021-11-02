package com.lee.leibo.ui.timeline;

import android.app.Application;

import androidx.annotation.NonNull;

import com.lee.leibo.api.WeiboApiService;
import com.lee.leibo.model.WeiboInfo;
import com.lee.leibo.model.WeiboResponse;
import com.lee.leibo.net.LeiboRequest;
import com.lee.leibo.net.RetrofitClient;
import com.lee.leibo.net.TokenManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

public class TimeLineViewModel extends BaseViewModel {
    public TimeLineViewModel(@NonNull Application application) {
        super(application);
    }

    private SingleLiveEvent<List<WeiboInfo>> weiboList = new SingleLiveEvent<>();

    public SingleLiveEvent<List<WeiboInfo>> getWeiboList() {
        return weiboList;
    }

    public void reqWeiboTimeLine() {
        LeiboRequest.builder()
                .setLifecycleProvider(getLifecycleProvider())
                .setObservable(
                        RetrofitClient
                                .getInstance()
                                .create(WeiboApiService.class)
                                .getWeiboList(TokenManager.getAccessToken(), Long.parseLong(TokenManager.getUid()))
                )
                .setObserver(new Observer<WeiboResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(WeiboResponse response) {
                        weiboList.setValue(response.getWeiboInfoList());
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
