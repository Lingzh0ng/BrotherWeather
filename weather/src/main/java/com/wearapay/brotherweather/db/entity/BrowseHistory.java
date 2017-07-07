package com.wearapay.brotherweather.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lyz on 2017/7/6.
 */
@Entity
public class BrowseHistory {
  @Id(autoincrement = true)
  private Long id;
  @NotNull
  private String gankioId;
  private Long time;

  @Generated(hash = 1397785178)
  public BrowseHistory(Long id, @NotNull String gankioId, Long time) {
      this.id = id;
      this.gankioId = gankioId;
      this.time = time;
  }

  @Generated(hash = 772159025)
  public BrowseHistory() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGankioId() {
    return gankioId;
  }

  public void setGankioId(String gankioId) {
    this.gankioId = gankioId;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  @Override public String toString() {
    return "BrowseHistory{" +
        "id=" + id +
        ", gankioId='" + gankioId + '\'' +
        ", time=" + time +
        '}';
  }
}
