package com.lee.leibo.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;

import com.lee.leibo.api.WeiboApiService;
import com.lee.leibo.model.UserInfo;
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

public class MainViewModel extends BaseViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    private SingleLiveEvent<UserInfo> userInfo = new SingleLiveEvent<>();

    public SingleLiveEvent<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void reqUserInfo() {
        LeiboRequest.builder()
                .setLifecycleProvider(getLifecycleProvider())
                .setObservable(
                        RetrofitClient
                                .getInstance()
                                .create(WeiboApiService.class)
                                .getUserInfo(TokenManager.getAccessToken(), Long.parseLong(TokenManager.getUid()), "")
                )
                .setObserver(new Observer<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserInfo response) {
                        userInfo.setValue(response);
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
