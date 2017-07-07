package com.wearapay.brotherweather.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wearapay.brotherweather.common.mvp.IBaseView;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lyz54 on 2017/6/27.
 */

public abstract class BaseFragment extends Fragment implements IBaseView {
    private boolean isNeedAddOnBackPressedListener = true;

    @Override public void showMessage(String message) {

    }

    @Override public void showMessage(int messageId) {

    }

    @Override public void showDiglog(String message) {

    }

    @Override public void showProgress(String message) {

    }

    @Override public void showProgress(int messageResourceId) {

    }

    @Override public void hideProgress() {

    }

    @Override public void processFail(Throwable t, String errorMessage) {

    }

    @Override public void navToHomePage() {

    }

    protected AtomicBoolean isViewActive;

    public abstract void onCleanBeforeDetach();

    protected void setIsNeedAddOnBackPressedListener(boolean need) {
        isNeedAddOnBackPressedListener = need;
    }

    protected boolean onBackPressed(boolean isFromKey) {
        if (!isFromKey) {
            ((BaseActivity) getActivity()).onBackPressed(isFromKey);
            return true;
        }
        return false;
    }

    private BaseActivity.OnBackPressedListener mOnBackPressedListener =
            new BaseActivity.OnBackPressedListener() {

                @Override
                public boolean onBackPressed() {
                    if (!BaseFragment.this.isHidden()) {
                        return BaseFragment.this.onBackPressed(true);
                    }

                    return false;
                }
            };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        isViewActive = new AtomicBoolean(true);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        isViewActive.set(false);
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (isNeedAddOnBackPressedListener) {
            ((BaseActivity) getActivity()).addOnBackPressedListener(mOnBackPressedListener);
        }
    }

    @Override
    public void onDetach() {
        ((BaseActivity) getActivity()).removeOnBackPressedListener(mOnBackPressedListener);

        onCleanBeforeDetach();
        super.onDetach();
    }
}
