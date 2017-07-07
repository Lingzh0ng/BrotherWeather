package com.wearapay.brotherweather.domain;

import java.util.List;

/**
 * Created by lyz on 2017/7/3.
 */
public class BaseResult<T> {
  private boolean error;
  private List<T> results;
  private String[] category;
  private int count;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String[] getCategory() {
    return category;
  }

  public void setCategory(String[] category) {
    this.category = category;
  }

  @Override public String toString() {
    return "BaseResult{" +
        "error=" + error +
        ", results=" + results +
        '}';
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public List<T> getResults() {
    return results;
  }

  public void setResults(List<T> results) {
    this.results = results;
  }
}
