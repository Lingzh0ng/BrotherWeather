package com.wearapay.brotherweather.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lyz on 2017/7/7.
 */
@Entity public class MainSetting {
  @Id private Long id;
  @NotNull private int pagerCount;
  @NotNull private String gankioTypes;
  @Generated(hash = 15030632)
  public MainSetting(Long id, int pagerCount, @NotNull String gankioTypes) {
      this.id = id;
      this.pagerCount = pagerCount;
      this.gankioTypes = gankioTypes;
  }
  @Generated(hash = 963296061)
  public MainSetting() {
  }
  public Long getId() {
      return this.id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public int getPagerCount() {
      return this.pagerCount;
  }
  public void setPagerCount(int pagerCount) {
      this.pagerCount = pagerCount;
  }
  public String getGankioTypes() {
      return this.gankioTypes;
  }
  public void setGankioTypes(String gankioTypes) {
      this.gankioTypes = gankioTypes;
  }
}
