package com.lee.leibo.ui.main;

import android.os.Bundle;

import com.lee.leibo.BR;
import com.lee.leibo.R;
import com.lee.leibo.databinding.ActivityMainBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}