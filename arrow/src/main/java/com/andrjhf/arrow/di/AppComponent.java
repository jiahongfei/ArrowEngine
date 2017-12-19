package com.andrjhf.arrow.di;

import android.app.Application;

import com.andrjhf.arrow.AppDelegateManager;
import com.andrjhf.arrow.http.IDataRepositoryManager;
import com.andrjhf.arrow.http.IHttpErrorHandler;
import com.andrjhf.arrow.http.IHttpResponseHandler;

import javax.inject.Singleton;

import dagger.Component;
import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * app全局提供的单例对象
 * Created by jiahongfei on 2017/11/1.
 */
@Singleton
@Component(modules = {ApplicationModule.class, DataRepositoryModule.class, AppDelegateConfig.class})
public interface AppComponent {

    void inject(AppDelegateManager delegate);

    Application getApplication();

    RxCache getRxCache();

    Retrofit getRetrofit();

    OkHttpClient providerOkHttpClient();

    IDataRepositoryManager providerRepositoryManager();

    @MockData
    IDataRepositoryManager providerMockRepositoryManager();

    IHttpErrorHandler providerIHttpErrorHandler();

    IHttpResponseHandler providerIHttpResponseHandler();
}
