package com.lee.leibo.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.lee.leibo.BR;
import com.lee.leibo.R;
import com.lee.leibo.constants.KeyConstant;
import com.lee.leibo.databinding.ActivityMainBinding;
import com.lee.leibo.net.TokenManager;
import com.lee.leibo.ui.timeline.TimeLineActivity;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private IWBAPI mWBAPI;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        binding.btnAuth.setOnClickListener(v -> {
            startClientAuth();
        });

        binding.btnEnterWeibo.setOnClickListener(view -> {
            startActivity(TimeLineActivity.class);
        });
    }

    @Override
    public void initData() {
        super.initData();
        initSdk();

        if (TokenManager.iamLoggedIn()) {
            startActivity(ProfileActivity.class);
            this.finish();
        }
    }

    //init sdk
    private void initSdk() {
        AuthInfo authInfo = new AuthInfo(this, KeyConstant.APP_KY, KeyConstant.REDIRECT_URL, KeyConstant.SCOPE);
        mWBAPI = WBAPIFactory.createWBAPI(this);
        mWBAPI.registerApp(this, authInfo);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mWBAPI.authorizeCallback(requestCode, resultCode, data);
    }


    private void startClientAuth() {
        mWBAPI.authorizeClient(new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                ToastUtils.showShort("??????????????????");
                binding.tvResult.setText("??????????????????");
                TokenManager.setAccessToken(token.getAccessToken());
                TokenManager.setRefreshToken(token.getRefreshToken());
                TokenManager.setLoginName(token.getScreenName());
                TokenManager.setUid(token.getUid());
                KLog.e("Name = " + TokenManager.getLoginName() + " token = " + TokenManager.getAccessToken());
            }

            @Override
            public void onError(UiError error) {
                ToastUtils.showShort("??????????????????");
                binding.tvResult.setText("??????????????????");
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("??????????????????");
                binding.tvResult.setText("??????????????????");
            }
        });
    }

}