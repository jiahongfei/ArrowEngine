package com.andrjhf.arrow.demo.weather;

import com.andrjhf.arrow.di.ActivityScope;
import com.andrjhf.arrow.di.AppComponent;

import dagger.Component;

/**
 * Created by jiahongfei on 2017/11/7.
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = WeatherModule.class)
public interface WeatherComponent {

    void inject(WeatherActivity weatherActivity);

}
