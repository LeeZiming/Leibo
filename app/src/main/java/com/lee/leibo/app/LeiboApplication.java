package com.lee.leibo.app;

import com.kunminx.architecture.BaseApplication;
import com.kunminx.architecture.utils.Utils;
import com.lee.leibo.BuildConfig;

/**
 * TODO tip 1：需要为项目准备一个 Application 来继承 BaseApplication，
 * 以便在 Activity/Fragment 中享用 Application 级作用域的 event-ViewModel
 * <p>
 * event-ViewModel 的职责仅限于在 "跨页面通信" 的场景下，承担 "唯一可信源"，
 * 所有跨页面的 "状态同步请求" 都交由该可信源在内部决策和处理，并统一分发给所有订阅者页面。
 * <p>
 * 如果这样说还不理解的话，详见《LiveData 鲜为人知的 身世背景 和 独特使命》中结合实际场合 对"唯一可信源"本质的解析。
 * https://xiaozhuanlan.com/topic/0168753249
 * <p>
 * Create by KunMinX at 19/10/29
 */


public class LeiboApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }
}
