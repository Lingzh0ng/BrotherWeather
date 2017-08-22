package com.wearapay.brotherweather.net.observer;

import com.wearapay.brotherweather.common.mvp.IBaseView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Created by lyz54 on 2017/6/28.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    private IBaseView view;

    public BaseObserver(IBaseView view) {
        this.view = view;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public abstract void onNext(@NonNull T t);

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        view.hideProgress();
        view.showMessage(e.getMessage());
    }

    @Override
    public void onComplete() {
        view.hideProgress();
    }
}
