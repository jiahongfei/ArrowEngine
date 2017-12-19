package com.andrjhf.arrow.demo.weather;

import com.andrjhf.arrow.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiahongfei on 2017/11/7.
 */
@ActivityScope
@Module
public class WeatherModule {

    private WeatherContract.WeatherView weatherView;

    public WeatherModule(WeatherContract.WeatherView weatherView){
        this.weatherView = weatherView;
    }

    @ActivityScope
    @Provides
    public WeatherContract.WeatherView providerWeatherView(){
        return weatherView;
    }

    @ActivityScope
    @Provides
    public WeatherContract.WeatherModel providerWeatherModel(WeatherModelImpl weatherModel){
        return weatherModel;
    }

    @ActivityScope
    @Provides
    public WeatherContract.WeatherPresenter providerWeatherPresenter(WeatherPresenterImpl weatherPresenter){
        return weatherPresenter;
    }

}
