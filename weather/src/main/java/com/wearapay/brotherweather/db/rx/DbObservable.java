package com.wearapay.brotherweather.db.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import org.greenrobot.greendao.AbstractDao;

/**
 * Created by lyz on 2017/7/7.
 */
public class DbObservable {
  private DbObservable() {
  }

  public static <T extends AbstractDao,K> Observable<K> apply(final T t, final Function<T,K> function) {

    return Observable.create(new DBOnSubscribe<T>(t)).flatMap(new Function<T, ObservableSource<K>>() {
      @Override public ObservableSource<K> apply(@NonNull T t) throws Exception {
        K apply = function.apply(t);
        return Observable.just(apply);
      }
    });
  }
}


