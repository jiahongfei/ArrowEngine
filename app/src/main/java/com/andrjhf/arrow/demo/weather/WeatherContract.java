package com.andrjhf.arrow.demo.weather;

import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.mvp.IModel;
import com.andrjhf.arrow.mvp.IPresenter;
import com.andrjhf.arrow.mvp.IView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by jiahongfei on 2017/11/7.
 */

public interface WeatherContract {

    interface WeatherView extends IView {

        void showWeather(String string);

    }

    interface WeatherPresenter extends IPresenter {

        void getWeather();

    }

    interface WeatherModel extends IModel {

        Observable<ApiResponse<Weather>> getWeather();

    }

}
