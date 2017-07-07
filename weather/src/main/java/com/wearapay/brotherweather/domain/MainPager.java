package com.wearapay.brotherweather.domain;

/**
 * Created by lyz on 2017/7/3.
 */
public class MainPager {
  private GankioType type;
  private String meiziUrl;
  private int currentPager;

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
}
