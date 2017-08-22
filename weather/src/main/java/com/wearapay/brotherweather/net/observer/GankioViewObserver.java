package com.wearapay.brotherweather.net.observer;

import com.wearapay.brotherweather.common.mvp.IBaseView;
import com.wearapay.brotherweather.domain.BaseResult;
import com.wearapay.brotherweather.exception.NetworkException;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.util.List;

/**
 * Created by lyz54 on 2017/6/28.
 */

public abstract class GankioViewObserver<T> implements Observer<BaseResult<T>> {

  private IBaseView view;

  @Override public void onSubscribe(@NonNull Disposable d) {

  }

  public GankioViewObserver(IBaseView view) {
    this.view = view;
  }

  public void onNext(@NonNull BaseResult<T> t) {
    if (t != null && t instanceof BaseResult) {
      boolean error = t.isError();
      if (error) {
        onError(new NetworkException("服务器错误"));
      } else {
        onSuccess(t.getResults());
      }
    } else {
      onError(new NetworkException("111"));
    }
  }

  protected abstract void onSuccess(List<T> t);

  @Override public void onError(@NonNull Throwable e) {
    e.printStackTrace();
    view.hideProgress();
    view.showMessage(e.getMessage());
  }

  @Override public void onComplete() {
    view.hideProgress();
  }
}
