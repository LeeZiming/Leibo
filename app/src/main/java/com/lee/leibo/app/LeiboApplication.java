package com.lee.leibo.app;

import com.lee.leibo.BuildConfig;
import com.lee.leibo.R;
import com.lee.leibo.ui.main.MainActivity;
import com.xuexiang.xui.XUI;

import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.utils.KLog;

public class LeiboApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //是否开启日志打印
        KLog.init(true);

        initCrash();
        initUI();
    }

    //配置全局异常崩溃操作
    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
                //.errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
                //.eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }

    /**
     * 初始化XUI 框架
     */
    private void initUI() {
        XUI.debug(LeiboApplication.isDebug());
    }


    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
