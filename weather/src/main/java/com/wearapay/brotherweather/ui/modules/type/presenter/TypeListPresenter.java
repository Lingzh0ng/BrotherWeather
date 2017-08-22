package com.wearapay.brotherweather.ui.modules.type.presenter;

import android.content.Context;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.db.entity.MainSetting;
import com.wearapay.brotherweather.net.observer.BaseObserver;
import com.wearapay.brotherweather.rep.DbRepository;
import com.wearapay.brotherweather.ui.modules.type.view.ITypeView;
import io.reactivex.annotations.NonNull;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/7/7.
 */
public class TypeListPresenter extends BasePresenter<ITypeView> {

  private DbRepository dbRepository;

  @Inject public TypeListPresenter(Context mContext, DbRepository dbRepository) {
    super(mContext);
    this.dbRepository = dbRepository;
  }

  public void queryMainSetting(){
    dbRepository.queryMainSetting().subscribe(new BaseObserver<MainSetting>(view) {
      @Override public void onNext(@NonNull MainSetting mainSetting) {
        view.displaySetting(mainSetting);
      }
    });
  }

  public void updateMainSetting(MainSetting mainSetting){
    dbRepository.updateMainSetting(mainSetting).subscribe(new BaseObserver<Boolean>(view) {
      @Override public void onNext(@NonNull Boolean aBoolean) {
        view.updateSetting(aBoolean);
      }
    });
  }
}
