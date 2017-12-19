package com.andrjhf.arrow;

import android.app.Application;

import com.andrjhf.arrow.di.AppComponent;
import com.andrjhf.arrow.di.AppDelegateConfig;
import com.andrjhf.arrow.di.ApplicationModule;
import com.andrjhf.arrow.di.DaggerAppComponent;
import com.andrjhf.arrow.di.DataRepositoryModule;

/**
 * Arrow框架全局配置类
 * Created by jiahongfei on 2017/11/6.
 */

public class AppDelegateManager {

    private AppComponent appComponent;

    private static final AppDelegateManager sAppDelegateManager = new AppDelegateManager();

    public static final AppDelegateManager getInstance() {
        return sAppDelegateManager;
    }

    protected AppDelegateManager() {
    }

    public final void init(Application application, AppDelegateConfig appDelegateConfig) {
        appComponent = DaggerAppComponent
                .builder()
                .applicationModule(new ApplicationModule(application))
                .dataRepositoryModule(new DataRepositoryModule())
                .appDelegateConfig(appDelegateConfig)
                .build();
        appComponent.inject(this);

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
