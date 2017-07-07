package com.wearapay.brotherweather.domain;

import android.text.TextUtils;

/**
 * Created by lyz on 2017/6/28.
 */
public class City {
  private String id;
  private String province;
  private String town;
  private String county;

  public City(String id, String province, String town, String county) {
    this.id = id;
    this.province = province;
    this.town = town;
    this.county = county;
  }

  public City(String content) {
    if (!TextUtils.isEmpty(content)) {
      String[] split = content.split(",");
      for (int i = 0; i < split.length; i++) {
        switch (i) {
          case 0:
            setId(split[0]);
            break;
          case 1:
            setCounty(split[1]);
            break;
          case 2:
            setTown(split[2]);
            break;
          case 3:
            setProvince(split[3]);
            break;
          default:
            break;
        }
      }
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override public String toString() {
    return "City{" +
        "id='" + id + '\'' +
        ", province='" + province + '\'' +
        ", town='" + town + '\'' +
        ", county='" + county + '\'' +
        '}';
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }
}