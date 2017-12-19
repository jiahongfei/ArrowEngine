package com.andrjhf.arrow.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;
import javax.inject.Scope;

/**
 * 网络请求使用本地的mockdata进行调试
 * Created by jiahongfei on 2017/11/6.
 */

@Qualifier//限定符
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MockData {
}

