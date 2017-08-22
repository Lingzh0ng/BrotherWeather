package com.wearapay.brotherweather.db.rx;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import org.greenrobot.greendao.AbstractDao;

/**
 * Created by lyz on 2017/7/7.
 */
public class DBOnSubscribe<T extends AbstractDao> implements ObservableOnSubscribe<T> {

  private T t;

  public DBOnSubscribe(T t) {
    this.t = t;
  }

  @Override public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
    if (t != null) {
      e.onNext(t);
      e.onComplete();
    } else {
      e.onError(new NullPointerException());
      e.onComplete();
    }
  }
}
