package com.wearapay.brotherweather.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lyz on 2017/7/6.
 */
@Entity public class SearchHistory {
  @Id(autoincrement = true) private Long id;
  @NotNull private String search;
  private Long time;

  @Generated(hash = 709962366)
  public SearchHistory(Long id, @NotNull String search, Long time) {
      this.id = id;
      this.search = search;
      this.time = time;
  }

  @Generated(hash = 1905904755)
  public SearchHistory() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public String getSearch() {
    return search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  @Override public String toString() {
    return "SearchHistory{" +
        "id=" + id +
        ", search='" + search + '\'' +
        ", time=" + time +
        '}';
  }
}
