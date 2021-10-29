package com.lee.leibo.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.blankj.utilcode.util.ToastUtils;
import com.lee.leibo.app.LeiboApplication;
import com.lee.leibo.R;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import me.goldze.mvvmhabit.utils.RxUtils;


public class LeiboRequest {

    public static LeiboRequest builder() {
        return new LeiboRequest();
    }

    private Observable mObservable;
    private Observer mObserver;

    private Consumer mNext;
    private Consumer<Throwable> mError;
    private Action mAction;
    private Consumer<Disposable> mOnSubscribe;

    private LifecycleProvider mLifecycleProvider;

    private LeiboRequest() {
    }

    public LeiboRequest setLifecycleProvider(LifecycleProvider lifecycleProvider) {
        mLifecycleProvider = lifecycleProvider;
        return this;
    }

    public LeiboRequest setObservable(Observable observable) {
        mObservable = observable;
        return this;
    }

    public LeiboRequest setObserver(Observer observer) {
        mObserver = observer;
        if (observer != null) {
            mNext = null;
            mError = null;
            mAction = null;
        }
        return this;
    }

    public LeiboRequest setOnSubscribe(Consumer<Disposable> onSubscribe) {
        mOnSubscribe = onSubscribe;
        return this;
    }

    public LeiboRequest setNext(Consumer next) {
        mNext = next;
        return this;
    }

    public LeiboRequest setError(Consumer<Throwable> error) {
        mError = error;
        return this;
    }

    public LeiboRequest setAction(Action action) {
        mAction = action;
        return this;
    }

    private void ready() {
        if (mLifecycleProvider != null) {
            //noinspection unchecked
            mObservable = mObservable.compose(RxUtils.bindToLifecycle(mLifecycleProvider));
        }
        //noinspection unchecked
        mObservable = mObservable.compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
        ;
    }

    @SuppressLint("CheckResult")
    public void build() {
        build(false);
    }

    @SuppressLint("CheckResult")
    public void build(boolean noNetWorkTips/*无需网络状态差提示*/) {
        boolean bisConnFlag = false;
        //1.获取网络连接的管理对象
        ConnectivityManager conManager = (ConnectivityManager) LeiboApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //2.通过管理者对象拿到网络的信息
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            //3.网络状态是否可用的返回值
            bisConnFlag = network.isAvailable();
        }
        if (!bisConnFlag) {
            // TODO Network disable
            if (!noNetWorkTips) {
                ToastUtils.showShort(R.string.toast_network_error);
            }
            Throwable netError = new TimeoutException("Network disable.");
            if (mObserver != null)
                mObserver.onError(netError);
            else if (mError != null) {
                try {
                    mError.accept(netError);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (mObservable != null) {
            ready();
            if (mObserver != null) {
                //noinspection unchecked
                mObservable.subscribe(mObserver);
            } else {
                if (mNext == null) {
                    mNext = Functions.emptyConsumer();
                }
                if (mError == null) {
                    mError = Functions.emptyConsumer();
                }
                if (mAction == null) {
                    mAction = Functions.EMPTY_ACTION;
                }
                if (mOnSubscribe == null) {
                    mOnSubscribe = Functions.emptyConsumer();
                }
                //noinspection ResultOfMethodCallIgnored,unchecked
                mObservable.subscribe(mNext, mError, mAction, mOnSubscribe);
            }
        }
    }

    public static class SimpleObserver<T> implements Observer<T> {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(T response) {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {
        }
    }
}
