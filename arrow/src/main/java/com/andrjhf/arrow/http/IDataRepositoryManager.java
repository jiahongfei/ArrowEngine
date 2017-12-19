package com.andrjhf.arrow.http;

/**
 * 获取网络Or本地mock数据的接口
 * Created by jiahongfei on 2017/11/7.
 */

public interface IDataRepositoryManager {

    <T> T getRepositoryDataService(Class<T> tClass);

    <T> T getRepositoryDataProvider(Class<T> tClass);

}
