package com.wearapay.brotherweather.ui.modules.type.presenter;

import android.content.Context;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.rep.DbRepository;
import com.wearapay.brotherweather.ui.modules.type.view.ITypeView;
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
    //dbRepository.
  }
}
