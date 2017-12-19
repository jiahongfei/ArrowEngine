package com.andrjhf.arrow.demo.login;

import com.andrjhf.arrow.di.ActivityScope;
import com.andrjhf.arrow.di.AppComponent;

import dagger.Component;

/**
 * Created by jiahongfei on 2017/11/24.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);

}
