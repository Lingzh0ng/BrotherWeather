package com.wearapay.brotherweather.net.observer;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Created by lyz54 on 2017/6/28.
 */

public abstract class BaseObserver<T> implements Observer<T> {


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public abstract void onNext(@NonNull T t);

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
