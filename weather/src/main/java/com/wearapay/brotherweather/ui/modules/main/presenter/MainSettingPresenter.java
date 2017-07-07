package com.wearapay.brotherweather.ui.modules.main.presenter;

import android.content.Context;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.db.entity.MainSetting;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioType;
import com.wearapay.brotherweather.domain.MainPager;
import com.wearapay.brotherweather.rep.IDbRepository;
import com.wearapay.brotherweather.ui.modules.main.view.IMainSettingView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class MainSettingPresenter extends BasePresenter<IMainSettingView> {
  IDbRepository dbRepository;

  @Inject public MainSettingPresenter(Context mContext, IDbRepository dbRepository) {
    super(mContext);
    this.dbRepository = dbRepository;
  }

  public void getMainSetting(final List<GankioData> meiziUrl) {
    dbRepository.queryMainSetting()
        .flatMap(new Function<MainSetting, ObservableSource<List<MainPager>>>() {
          @Override public ObservableSource<List<MainPager>> apply(@NonNull MainSetting mainSetting)
              throws Exception {
            List<MainPager> list = new ArrayList<>(mainSetting.getPagerCount());
            String[] split = mainSetting.getGankioTypes().split(",");
            for (int i = 0; i < mainSetting.getPagerCount(); i++) {
              GankioType gankioType = GankioType.valueOf(split[i]);
              list.add(i, new MainPager(gankioType, meiziUrl != null ? meiziUrl.get(i).getUrl() : null, i));
            }
            return Observable.just(list);
          }
        })
        .subscribe(new Consumer<List<MainPager>>() {
          @Override public void accept(@NonNull List<MainPager> mainPagerList) throws Exception {
            view.displayMainPager(mainPagerList);
          }
        });
  }

  public void setMainSetting(MainSetting mainSetting) {

  }
}
