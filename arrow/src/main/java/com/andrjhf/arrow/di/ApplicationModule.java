package com.andrjhf.arrow.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 提供全局的Application实例
 * Created by jiahongfei on 2017/11/7.
 */

@Singleton
@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    public Application getApplication(){
        return this.application;
    }

}
