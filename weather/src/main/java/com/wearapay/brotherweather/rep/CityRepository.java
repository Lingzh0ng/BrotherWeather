package com.wearapay.brotherweather.rep;

import android.os.Environment;
import com.wearapay.brotherweather.api.ICityRestService;
import com.wearapay.brotherweather.domain.City;
import com.wearapay.brotherweather.utils.CityUtils;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.ResponseBody;

/**
 * Created by lyz54 on 2017/6/28.
 */
@Singleton
public class CityRepository implements ICityRepository {
    ICityRestService service;

    @Inject
    public CityRepository(ICityRestService service) {
        this.service = service;
    }

    @Override
    public Observable<List<City>> getCityInfo(String id) {
        return service.getCityInfo(id).subscribeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, Observable<List<City>>>() {
                    @Override
                    public Observable<List<City>> apply(@NonNull ResponseBody responseBody) throws Exception {
                        InputStream is;
                        FileOutputStream fos;
                        File file = new File(Environment.getExternalStorageDirectory().getPath(), "city.txt");
                        try {
                            is = responseBody.byteStream();
                            // 判断文件目录是否存在
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            fos = new FileOutputStream(file);
                            int len = -1;
                            // 缓存
                            byte[] buffer = new byte[4096];
                            // 写入到文件中
                            while ((len = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return Observable.just(CityUtils.copyCity(file.getPath()));
                    }
                });

    }


}
