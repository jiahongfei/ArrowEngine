package com.andrjhf.arrow.di;

import android.app.Application;

import com.andrjhf.arrow.http.DataRepositoryManager;
import com.andrjhf.arrow.http.IDataRepositoryManager;
import com.andrjhf.arrow.http.MockDataRespsitoryManager;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 提供网络访问数据方面的实例
 * Created by jiahongfei on 2017/11/6.
 */

@Singleton
@Module
public class DataRepositoryModule {

    private static final long TIMEOUT = 10;  //10s

    @Singleton
    @Provides
    RxCache providerRxCache(Application application, File cacheDir, @Nullable RxCacheConfig rxCacheConfig) {
        RxCache.Builder builder = new RxCache.Builder();

        if (null != rxCacheConfig) {
            rxCacheConfig.configRxCache(application, builder);
        }
        return builder.persistence(cacheDir, new GsonSpeaker());
    }

    @Singleton
    @Provides
    Retrofit providerRetrofit(Application application, String baseUrl, OkHttpClient okHttpClient, @Nullable RetrofitConfig retrofitConfig) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient);

        //这个配置可以刷新上面的设置
        if (null != retrofitConfig) {
            retrofitConfig.configRetrofit(application, builder);
        }

        return builder.build();//03执行操作
    }

    @Singleton
    @Provides
    OkHttpClient providerOkHttpClient(Application application, @Nullable OkhttpConfig okhttpConfig) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS);

        //这个配置可以刷新上面的设置
        if (null != okhttpConfig) {
            okhttpConfig.configOkhttp(application, builder);
        }

        return builder.build();
    }

    @Singleton
    @Provides
    public IDataRepositoryManager providerRepositoryManager(Retrofit retrofit, RxCache rxCache) {
        return new DataRepositoryManager(retrofit, rxCache);
    }

    @Singleton
    @Provides
    @MockData
    public IDataRepositoryManager providerMockRepositoryManager(Application application, @Nullable DataRepositoryModule.MockDataConfig mockDataConfig) {
        return new MockDataRespsitoryManager(application,mockDataConfig);
    }

    public interface RetrofitConfig {
        void configRetrofit(Application context, Retrofit.Builder builder);
    }

    public interface OkhttpConfig {
        void configOkhttp(Application context, OkHttpClient.Builder builder);
    }

    public interface RxCacheConfig {
        void configRxCache(Application context, RxCache.Builder builder);
    }

    public interface MockDataConfig{
        String configMockDataConfig(Application context,Method method);
    }
}
