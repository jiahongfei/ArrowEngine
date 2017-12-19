package com.andrjhf.arrow.demo.weather;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.http.HttpDisposableObserver;
import com.andrjhf.arrow.http.IHttpErrorHandler;
import com.andrjhf.arrow.http.IHttpResponseHandler;
import com.andrjhf.arrow.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by jiahongfei on 2017/11/7.
 */

public class WeatherPresenterImpl
        extends BasePresenter<WeatherContract.WeatherModel, WeatherContract.WeatherView>
        implements WeatherContract.WeatherPresenter {

    private static final String TAG = "WeatherPresenterImpl";

    private IHttpErrorHandler iHttpErrorHandler;
    private IHttpResponseHandler iHttpResponseHandler;

    @Inject
    public WeatherPresenterImpl(WeatherContract.WeatherModel model,
                                WeatherContract.WeatherView view,
                                IHttpErrorHandler iHttpErrorHandler,
                                IHttpResponseHandler iHttpResponseHandler) {
        super(model, view);
        this.iHttpErrorHandler = iHttpErrorHandler;
        this.iHttpResponseHandler = iHttpResponseHandler;
    }


    @Override
    public void getWeather() {

        HttpDisposableObserver disposableObserver = new HttpDisposableObserver<ApiResponse<Weather>>() {

            @Override
            public void onStart() {
                view.showLoading();
            }

            @Override
            public void onResponse(@NonNull ApiResponse<Weather> weatherApiResponse) {
                String response = weatherApiResponse.toString();
                Log.e(TAG, response);
                view.showWeather(response);
            }

            @Override
            public void onFinish() {
                view.hideLoading();

            }

            @Override
            public void onError(@NonNull Throwable e) {
                super.onError(e);
                Log.e(TAG, e.toString());
                view.hideLoading();

            }

        };

        registerDispose(disposableObserver);

        model.getWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Function<ApiResponse<Weather>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull ApiResponse<Weather> weatherApiResponse) throws Exception {
//                        Log.e(TAG, weatherApiResponse.getData().toString());
////                        String s = "{\"code\":\"1\",\"msg\":\"lalallala\",\"data\":{\"city\":\"北京\",\"cityid\":\"101010100\",\"temp\":\"10\",\"WD\":\"东南风\",\"WS\":\"2级\",\"SD\":\"26%\",\"WSE\":\"2\",\"time\":\"10:25\",\"isRadar\":\"1\",\"Radar\":\"JC_RADAR_AZ9010_JB\",\"njd\":\"暂无实况\",\"qy\":\"1012\"}}";
//                        String s = "  {\"code\":\"2\",\"msg\":\"lalallala\",\"data\":[{\"city\":\"北京\",\"cityid\":\"101010100\",\"temp\":\"10\",\"WD\":\"东南风\",\"WS\":\"2级\",\"SD\":\"26%\",\"WSE\":\"2\",\"time\":\"10:25\",\"isRadar\":\"1\",\"Radar\":\"JC_RADAR_AZ9010_JB\",\"njd\":\"暂无实况\",\"qy\":\"1012\"}]}";
//                        ApiResponse<List<Weather>> apiResponse = JSON.parseObject(s,new TypeReference<ApiResponse<List<Weather>>>(){});
//                        return Observable.just(apiResponse);
//                    }
//                })
                .subscribe(disposableObserver);


//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
//                while (true){
//                    TimeUnit.MILLISECONDS.sleep(100);
//                    Log.e(TAG,"11111111");
//                }
//            }
//        }).subscribeOn(Schedulers.newThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//
//                    }
//                });

    }

    @Override
    public void onDestory() {
        super.onDestory();
    }
}
