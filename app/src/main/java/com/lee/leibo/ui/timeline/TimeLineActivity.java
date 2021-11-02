package com.lee.leibo.ui.timeline;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lee.leibo.BR;
import com.lee.leibo.R;
import com.lee.leibo.databinding.ActivityTimeLineBinding;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import me.goldze.mvvmhabit.base.BaseActivity;

public class TimeLineActivity extends BaseActivity<ActivityTimeLineBinding, TimeLineViewModel> {

    WeiboAdapter mWeiboAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_time_line;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        initRefresh();
        initRecyclerView();

        viewModel.reqWeiboTimeLine();

        observeWeiboData();
    }

    @Override
    public void initData() {
        super.initData();
    }

    private void initRefresh() {
        binding.refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        binding.refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        binding.refreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        });
        binding.refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        });
    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWeiboAdapter = new WeiboAdapter(null);
        mWeiboAdapter.setAnimationEnable(true);
        binding.recyclerView.setAdapter(mWeiboAdapter);

    }

    private void observeWeiboData() {
        viewModel.getWeiboList().observe(this, data -> {
            mWeiboAdapter.addData(data);
        });
    }

}