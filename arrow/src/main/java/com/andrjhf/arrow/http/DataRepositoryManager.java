package com.andrjhf.arrow.http;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 访问网络的管理类
 * Created by jiahongfei on 2017/11/6.
 */

public class DataRepositoryManager implements IDataRepositoryManager {

    private static final String TAG = "DataRepositoryManager";

    private static final String RETROFIT = "retrifit";
    private static final String RXCACHE = "rxcache";

    private Retrofit retrofit;
    private RxCache rxCache;

    private static Map<String, Object> cacheMap = new HashMap<>();

    @Inject
    public DataRepositoryManager(Retrofit retrofit, RxCache rxCache){
        this.retrofit = retrofit;
        this.rxCache = rxCache;
    }

    @Override
    public synchronized  <T> T getRepositoryDataService(Class<T> tClass){
        T t = (T) cacheMap.get(RETROFIT);
        if(null == t){
            t = retrofit.create(tClass);
            cacheMap.put(RETROFIT, t);
        }
        return t;
    }

    @Override
    public synchronized  <T> T getRepositoryDataProvider(Class<T> tClass){
        T t = (T) cacheMap.get(RXCACHE);
        if(null == t){
            t = rxCache.using(tClass);
            cacheMap.put(RXCACHE, t);
        }
        return t;
    }

}
