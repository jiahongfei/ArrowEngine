package com.andrjhf.arrow.demo.weather;

import com.andrjhf.arrow.demo.ServiceApi;
import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.http.IDataRepositoryManager;
import com.andrjhf.arrow.mvp.BaseModel;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by jiahongfei on 2017/11/7.
 */

public class WeatherModelImpl extends BaseModel implements WeatherContract.WeatherModel {

    @Inject
    public WeatherModelImpl( IDataRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<ApiResponse<Weather>> getWeather() {
        return repositoryManager.getRepositoryDataService(ServiceApi.class).getWeather();
    }

}
