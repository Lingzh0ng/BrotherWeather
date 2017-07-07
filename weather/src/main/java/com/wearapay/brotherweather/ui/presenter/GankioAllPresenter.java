package com.wearapay.brotherweather.ui.presenter;

import android.content.Context;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.domain.BaseResult;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioType;
import com.wearapay.brotherweather.net.observer.ViewObserver;
import com.wearapay.brotherweather.rep.DbRepository;
import com.wearapay.brotherweather.rep.GankioRepository;
import com.wearapay.brotherweather.ui.view.IGankioView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class GankioAllPresenter extends BasePresenter<IGankioView> {
  GankioRepository gankioRepository;
  DbRepository dbRepository;

  @Inject public GankioAllPresenter(Context mContext, GankioRepository gankioRepository,
      DbRepository dbRepository) {
    super(mContext);
    this.gankioRepository = gankioRepository;
    this.dbRepository = dbRepository;
  }

  public void getGankioData(GankioType type, int count, int page, boolean isProgress) {
    if (isProgress) {
      view.showProgress("");
    }
    wrap(gankioRepository.getAllGankioData(type, count, page)).flatMap(
        new Function<BaseResult<GankioData>, ObservableSource<BaseResult<GankioData>>>() {
          @Override public ObservableSource<BaseResult<GankioData>> apply(
              @NonNull BaseResult<GankioData> gankioDataBaseResult) throws Exception {
            //List<GankioData> results = ;
            for (GankioData gankioData : gankioDataBaseResult.getResults()) {
              boolean b = dbRepository.queryBrowseHistory(gankioData.get_id());
              gankioData.setBrowseHistory(b);
            }
            return Observable.just(gankioDataBaseResult);
          }
        }).subscribe(new ViewObserver<GankioData>(view) {
      @Override protected void onSuccess(List<GankioData> t) {
        view.display(t);
      }

      @Override public void onError(@NonNull Throwable e) {
        super.onError(e);
        view.displayError();
      }
    });
  }

  public void getGankioData(GankioType type, int count, int page) {
    getGankioData(type, count, page, true);
  }

  public void setBrowseHistory(String gankioId) {
    dbRepository.addBrowseHistory(gankioId);
  }
}
