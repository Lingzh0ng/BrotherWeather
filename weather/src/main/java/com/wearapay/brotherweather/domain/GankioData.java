package com.wearapay.brotherweather.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyz on 2017/7/3.
 */
public class GankioData implements Serializable {

  /**
   * _id : 593f20e5421aa92c73b64806
   * createdAt : 2017-06-13T07:16:53.166Z
   * desc : 在 iOS 上运行 keras 深度学习组件。
   * publishedAt : 2017-06-15T13:55:57.947Z
   * source : chrome
   * type : iOS
   * url : https://github.com/atveit/keras2ios
   * used : true
   * who : S
   */

  private String _id;
  private List<String> images;
  private String createdAt;
  private String desc;
  private String publishedAt;
  private String source;
  private String type;
  private String url;
  private boolean used;
  private String who;

  private boolean browseHistory;

  public boolean isBrowseHistory() {
    return browseHistory;
  }

  public void setBrowseHistory(boolean browseHistory) {
    this.browseHistory = browseHistory;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getPublishedAt() {
    return publishedAt;
  }

  public void setPublishedAt(String publishedAt) {
    this.publishedAt = publishedAt;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getType() {
    return type;
  }

  @Override public String toString() {
    return "GankioData{" +
        "_id='" + _id + '\'' +
        ", images=" + images +
        ", createdAt='" + createdAt + '\'' +
        ", desc='" + desc + '\'' +
        ", publishedAt='" + publishedAt + '\'' +
        ", source='" + source + '\'' +
        ", type='" + type + '\'' +
        ", url='" + url + '\'' +
        ", used=" + used +
        ", who='" + who + '\'' +
        '}';
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public boolean isUsed() {
    return used;
  }

  public void setUsed(boolean used) {
    this.used = used;
  }

  public String getWho() {
    return who;
  }

  public void setWho(String who) {
    this.who = who;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }
}
