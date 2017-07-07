package com.wearapay.brotherweather.common.mvp;

/**
 * Created by lyz54 on 2017/6/27.
 */

public interface IBaseView {
    void showMessage(String message);

    void showMessage(int messageId);

    void showDiglog(String message);

    void showProgress(String message);

    void showProgress(int messageResourceId);

    void hideProgress();

    void processFail(Throwable t, String errorMessage);

    void navToHomePage();
}
