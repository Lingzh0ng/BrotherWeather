package com.wearapay.brotherweather.domain;

/**
 * Created by lyz on 2017/7/3.
 */
public enum GankioType {
  Android("Android"),
  Fuli("福利"),
  Release("休息视频"),
  Other("拓展资源"),
  Web("前端"),
  IOS("iOS");

  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  GankioType(String type) {
    this.type = type;
  }
}
