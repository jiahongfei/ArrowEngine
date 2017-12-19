package com.andrjhf.arrow.di;


import android.app.Application;

import com.andrjhf.arrow.di.DataRepositoryModule.RetrofitConfig;
import com.andrjhf.arrow.http.DataRepositoryManager;
import com.andrjhf.arrow.http.DefaultHttpErrorHandler;
import com.andrjhf.arrow.http.DefaultHttpResponseHandler;
import com.andrjhf.arrow.http.DefaultMockDataConfig;
import com.andrjhf.arrow.http.IHttpErrorHandler;
import com.andrjhf.arrow.http.IHttpResponseHandler;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.Nullable;

/**
 * 对arrow框架的一些配置
 * Created by jiahongfei on 2017/11/6.
 */

@Singleton
@Module
public class AppDelegateConfig {

    private final String baseUrl;
    private final File cacheDir;
    private final RetrofitConfig retrofitConfig;
    private final DataRepositoryModule.OkhttpConfig okhttpConfig;
    private final DataRepositoryModule.RxCacheConfig rxCacheConfig;
    private final DataRepositoryModule.MockDataConfig mockDataConfig;
    private final IHttpErrorHandler iHttpErrorHandler;
    private final IHttpResponseHandler iHttpResponseHandler;

    private AppDelegateConfig(Builder builder){
        baseUrl = builder.baseUrl;
        cacheDir = builder.cacheDir;
        retrofitConfig = builder.retrofitConfig;
        okhttpConfig = builder.okhttpConfig;
        rxCacheConfig = builder.rxCacheConfig;
        mockDataConfig = builder.mockDataConfig;

        iHttpErrorHandler = builder.iHttpErrorHandler;
        iHttpResponseHandler = builder.iHttpResponseHandler;
    }

    @Singleton
    @Provides
    public String providerBaseUrl() {
        return baseUrl;
    }

    @Singleton
    @Provides
    public File providerCacheDir() {
        return cacheDir;
    }

    @Singleton
    @Provides
    @Nullable
    public RetrofitConfig providerRetrofitConfig() {
        return retrofitConfig;
    }

    @Singleton
    @Provides
    @Nullable
    public DataRepositoryModule.OkhttpConfig providerOkhttpConfig() {
        return okhttpConfig;
    }

    @Singleton
    @Provides
    @Nullable
    public DataRepositoryModule.RxCacheConfig providerRxCacheConfig() {
        return rxCacheConfig;
    }

    @Singleton
    @Provides
    @Nullable
    public DataRepositoryModule.MockDataConfig providerMockDataConfig(){
        if(null == mockDataConfig){
             return new DefaultMockDataConfig();
        }
        return mockDataConfig;
    }

    @Singleton
    @Provides
    public IHttpErrorHandler providerIHttpErrorHandler(Application application) {
        if(null == iHttpErrorHandler){
            return new DefaultHttpErrorHandler(application);
        }
        return iHttpErrorHandler;
    }

    @Singleton
    @Provides
    public IHttpResponseHandler providerIHttpResponseHandler() {
        if(null == iHttpResponseHandler){
            return new DefaultHttpResponseHandler();
        }
        return iHttpResponseHandler;
    }

    public static class Builder{

        private String baseUrl;
        private File cacheDir;
        private RetrofitConfig retrofitConfig;
        private DataRepositoryModule.OkhttpConfig okhttpConfig;
        private DataRepositoryModule.RxCacheConfig rxCacheConfig;
        private DataRepositoryModule.MockDataConfig mockDataConfig;
        private IHttpErrorHandler iHttpErrorHandler;
        private IHttpResponseHandler iHttpResponseHandler;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setCacheDir(File cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        public Builder setRetrofitConfig(RetrofitConfig retrofitConfig) {
            this.retrofitConfig = retrofitConfig;
            return this;
        }

        public Builder setOkhttpConfig(DataRepositoryModule.OkhttpConfig okhttpConfig) {
            this.okhttpConfig = okhttpConfig;
            return this;
        }

        public Builder setRxCacheConfig(DataRepositoryModule.RxCacheConfig rxCacheConfig) {
            this.rxCacheConfig = rxCacheConfig;
            return this;
        }

        public Builder setIHttpErrorHandler(IHttpErrorHandler iHttpErrorHandler){
            this.iHttpErrorHandler = iHttpErrorHandler;
            return this;
        }

        public Builder setiHttpResponseHandler(IHttpResponseHandler iHttpResponseHandler) {
            this.iHttpResponseHandler = iHttpResponseHandler;
            return this;
        }

        public Builder setMockDataConfig(DataRepositoryModule.MockDataConfig mockDataConfig) {
            this.mockDataConfig = mockDataConfig;
            return this;
        }

        public AppDelegateConfig builder(){
            return new AppDelegateConfig(this);
        }

    }

}
