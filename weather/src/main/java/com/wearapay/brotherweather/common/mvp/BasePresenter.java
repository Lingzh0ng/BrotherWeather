package com.wearapay.brotherweather.common.mvp;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lyz54 on 2017/6/27.
 */

public class BasePresenter<T> {
    private Context mContext;
    protected T view;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    public void setView(T view) {
        this.view = view;
    }

    public void onDestroy() {
        mContext = null;
        view = null;
    }

    protected <T> Observable<T> wrap(Observable<T> origin) {
        return origin
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
