package com.lee.leibo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.lee.leibo.BR;
import com.lee.leibo.R;
import com.lee.leibo.databinding.ActivityMainBinding;
import com.lee.leibo.net.TokenManager;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private static final String APP_KY = "1335885399";
    private static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    private static final String SCOPE = "";

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

    }

    @Override
    public void initData() {
        super.initData();
        initSdk();
        viewModel.reqDefaultShippingAddress();
    }

    //init sdk
    private void initSdk() {
        AuthInfo authInfo = new AuthInfo(this, APP_KY, REDIRECT_URL, SCOPE);
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
                ToastUtils.showShort("微博授权成功");
                binding.tvResult.setText("微博授权成功");
                TokenManager.setAccessToken(token.getAccessToken());
                TokenManager.setRefreshToken(token.getRefreshToken());
                TokenManager.setLoginName(token.getScreenName());
                TokenManager.setUid(token.getUid());
                KLog.e("Name = " + TokenManager.getLoginName() + " token = " + TokenManager.getAccessToken());
            }

            @Override
            public void onError(UiError error) {
                ToastUtils.showShort("微博授权出错");
                binding.tvResult.setText("微博授权出错");
            }

            @Override
            public void onCancel() {
                ToastUtils.showShort("微博授权取消");
                binding.tvResult.setText("微博授权取消");
            }
        });
    }

}