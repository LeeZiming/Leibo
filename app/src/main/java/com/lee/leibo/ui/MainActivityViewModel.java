package com.lee.leibo.ui;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    public final ObservableBoolean isDrawerOpened = new ObservableBoolean();

    //TODO 此处用于绑定的状态，使用 LiveData 而不是 ObservableField，
    // 主要是考虑到 ObservableField 具有防抖的特性，不适合该场景。

    //如果这么说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350

    public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>(false);

    public final MutableLiveData<Boolean> allowDrawerOpen = new MutableLiveData<>(true);

    //TODO tip 2：将 request 作为 ViewModel 的成员暴露给 Activity/Fragment，
    // 如此便于语义的明确，以及实现多个 request 在 ViewModel 中的组合和复用。

    //如果这样说还不理解的话，详见《如何让同事爱上架构模式、少写 bug 多注释》的解析
    //https://xiaozhuanlan.com/topic/8204519736

}
