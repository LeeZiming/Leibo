package com.lee.leibo.ui.main;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.lee.leibo.BR;
import com.lee.leibo.R;
import com.lee.leibo.app.LeiboApplication;
import com.lee.leibo.databinding.ActivityProfileBinding;
import com.lee.leibo.model.UserInfo;

import me.goldze.mvvmhabit.base.BaseActivity;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, MainViewModel> {

    UserInfo mUserInfo = new UserInfo();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_profile;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.reqUserInfo();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.getUserInfo().observe(this, data -> {
            mUserInfo = data;
            showUserInfo(data);
        });
    }

    private void showUserInfo(UserInfo userInfo) {
        Glide.with(LeiboApplication.getInstance())
                .load(userInfo.getAvatarLarge())
                .into(binding.ivAvator);
        binding.tvNickName.setText(userInfo.getScreenName());
    }
}
