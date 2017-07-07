package com.wearapay.brotherweather.rep;

/**
 * Created by lyz on 2017/7/6.
 */
public interface IDbRepository {
  void addBrowseHistory(String gankioId);

  boolean queryBrowseHistory(String gankioId);
}
