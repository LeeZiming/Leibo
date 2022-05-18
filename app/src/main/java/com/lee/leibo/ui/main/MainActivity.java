package com.lee.leibo.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.lee.leibo.BR;
import com.lee.leibo.R;
import com.lee.leibo.domain.message.DrawerCoordinateManager;
import com.lee.leibo.domain.message.SharedViewModel;
import com.lee.leibo.ui.MainActivityViewModel;

import java.util.List;

public class MainActivity extends BaseActivity {

    private MainActivityViewModel mState;
    private SharedViewModel mEvent;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(MainActivityViewModel.class);
        mEvent = getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {//TODO tip 1: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
        // 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。
        // 而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。

        // 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910

        return new DataBindingConfig(R.layout.activity_main, BR.vm, mState)
                .addBindingParam(BR.listener, new ListenerHandler());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO tip：Android 12 部分权限的处理

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            XXPermissions.with(this)
                    .permission(Permission.READ_PHONE_STATE)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            init();
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            finish();
                        }
                    });
        } else {
            init();
        }
    }

    private void init() {
        mEvent.isToCloseActivityIfAllowed().observe(this, aBoolean -> {
            NavController nav = Navigation.findNavController(this, R.id.main_fragment_host);
            if (nav.getCurrentDestination() != null && nav.getCurrentDestination().getId() != R.id.mainFragment) {
                nav.navigateUp();

            } else if (mState.isDrawerOpened.get()) {

                //TODO 同 tip 2

                mEvent.requestToOpenOrCloseDrawer(false);

            } else {
                super.onBackPressed();
            }
        });

        mEvent.isToOpenOrCloseDrawer().observe(this, aBoolean -> {

            //TODO yes：同 tip 1: 此处将 drawer 的 open 和 close 都放在 drawerBindingAdapter 中操作，规避了视图实例 null 安全的一致性问题，

            //因为 横屏布局 根本就没有 drawerLayout。此处如果用传统的视图实例 null 安全方式，会很容易因疏忽而造成空引用。

            //TODO 此外，此处为 drawerLayout 绑定的状态 "openDrawer"，使用 LiveData 而不是 ObservableField，主要是考虑到 ObservableField 具有 "防抖" 的特性，不适合该场景。

            //如果这么说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350

            mState.openDrawer.setValue(aBoolean);

            //TODO do not:（容易因疏忽 而埋下视图实例 null 安全的一致性隐患）

            /*if (mBinding.dl != null) {
                if (aBoolean && !mBinding.dl.isDrawerOpen(GravityCompat.START)) {
                    mBinding.dl.openDrawer(GravityCompat.START);
                } else {
                    mBinding.dl.closeDrawer(GravityCompat.START);
                }
            }*/
        });

        DrawerCoordinateManager.getInstance().isEnableSwipeDrawer().observe(this, aBoolean -> {

            //TODO yes: 同 tip 1

            mState.allowDrawerOpen.setValue(aBoolean);

            // TODO do not:（容易因疏忽 而埋下视图实例 null 安全的一致性隐患）

            /*if (mBinding.dl != null) {
                mBinding.dl.setDrawerLockMode(aBoolean
                        ? DrawerLayout.LOCK_MODE_UNLOCKED
                        : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }*/
        });
    }

    public class ListenerHandler extends DrawerLayout.SimpleDrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            mState.isDrawerOpened.set(true);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            mState.isDrawerOpened.set(false);
            mState.openDrawer.setValue(false);
        }
    }
}