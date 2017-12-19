package com.andrjhf.arrow.demo.weather;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.andrjhf.arrow.demo.R;
import com.andrjhf.arrow.mvp.BaseActivity;

public class WeatherActivity extends BaseActivity<WeatherContract.WeatherPresenter>
        implements WeatherContract.WeatherView{

    private static final String TAG = "WeatherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        DaggerWeatherComponent
                .builder()
                .appComponent(appComponent)
                .weatherModule(new WeatherModule(this))
                .build()
                .inject(this);

        mPresenter.getWeather();

    }

    @Override
    public void showLoading() {
        Log.e(TAG, "showLoading");
    }

    @Override
    public void hideLoading() {
        Log.e(TAG, "hideLoading");

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showWeather(String string) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(string);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        CustomApplication.getWeathertRefWatcher().watch(this);
    }
}
