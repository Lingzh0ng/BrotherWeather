package com.wearapay.brotherweather.api;

import com.wearapay.brotherweather.domain.BaseResult;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioDayData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lyz on 2017/7/3.
 */
public interface IGankioRestService {
  /**
   * 新增：
   *
   * 所有干货，支持配图数据返回 （除搜索 Api）。
   * 例如：
   * http://gank.io/api/data/Android/10/1
   */
  @GET("data/{type}/{count}/{page}") Observable<BaseResult<GankioData>> getAllGankioData(
      @Path("type") String type, @Path("count") int count, @Path("page") int page);

  /**
   * 随机数据：http://gank.io/api/random/data/分类/个数
   *
   * 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
   * 个数： 数字，大于0
   * 例：
   * http://gank.io/api/random/data/Android/20
   */
  @GET("random/data/{type}/{count}") Observable<BaseResult<GankioData>> getRandomGankioData(
      @Path("type") String type, @Path("count") int count);

  /**
   * 2016-6-12 日更新：
   *
   * 搜索 API
   *
   * http://gank.io/api/search/query/listview/category/Android/count/10/page/1
   * 注：
   * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
   * count 最大 50
   */
  @GET("search/query/listview/category/{type}/count/{count}/page/{page}")
  Observable<BaseResult<GankioData>> searchGankio(@Path("type") String type,
      @Path("count") int count, @Path("page") int page);

  /**
   * 获取某几日干货网站数据:
   *
   * http://gank.io/api/history/content/2/1
   *
   * 注： 2 代表 2 个数据，1 代表：取第一页数据
   */
  @GET("history/content/{count}/{page}") Observable<BaseResult<GankioDayData>> getSomeDayData(
      @Path("count") int count, @Path("page") int page);

  /**
   * 获取特定日期网站数据:
   *
   * http://gank.io/api/history/content/day/2016/05/11
   */
  @GET("history/content/day/{year}/{month}/{day}") Observable<BaseResult<GankioDayData>> getOneDayData(
      @Path("year") String year, @Path("month") String month, @Path("day") String day);

  /**
   * 获取发过干货日期接口:
   *
   * http://gank.io/api/day/history 方式 GET
   */
  @GET("day/history") Observable<BaseResult<String>> getTimeList();
}
