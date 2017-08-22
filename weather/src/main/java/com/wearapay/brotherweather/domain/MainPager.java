package com.wearapay.brotherweather.domain;

import java.io.Serializable;

/**
 * Created by lyz on 2017/7/3.
 */
public class MainPager implements Serializable{
  private GankioType type;
  private String meiziUrl;
  private int currentPager;
  private boolean select;

  public boolean isSelect() {
    return select;
  }

  public void setSelect(boolean select) {
    this.select = select;
  }

  public int getCurrentPager() {
    return currentPager;
  }

  public void setCurrentPager(int currentPager) {
    this.currentPager = currentPager;
  }

  public MainPager(GankioType type, String meiziUrl,int currentPager) {
    this.type = type;
    this.meiziUrl = meiziUrl;
    this.currentPager = currentPager;
    select = true;

  }

  @Override public String toString() {
    return "MainPager{" +
        "type=" + type +
        ", meiziUrl='" + meiziUrl + '\'' +
        ", currentPager=" + currentPager +
        ", select=" + select +
        '}';
  }

  public String getMeiziUrl() {
    return meiziUrl;
  }

  public void setMeiziUrl(String meiziUrl) {
    this.meiziUrl = meiziUrl;
  }

  public GankioType getType() {

    return type;
  }

  public void setType(GankioType type) {
    this.type = type;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MainPager mainPager = (MainPager) o;

    return type == mainPager.type;
  }

  @Override public int hashCode() {
    return type != null ? type.hashCode() : 0;
  }
}
