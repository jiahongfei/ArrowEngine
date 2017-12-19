package com.andrjhf.arrow.demo.login;

import android.util.Log;

import com.andrjhf.arrow.di.ActivityScope;
import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.http.HttpDisposableObserver;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiahongfei on 2017/11/24.
 */
@ActivityScope
@Module
public class LoginModule {

    private LoginContract.LoginView loginView;

    public LoginModule(LoginContract.LoginView loginView){
        this.loginView = loginView;
    }

    @ActivityScope
    @Provides
    public LoginContract.LoginView providerLoginView(){
        return loginView;
    }

    @ActivityScope
    @Provides
    public LoginContract.LoginInteractor providerLoginInteractor(LoginInteractorImpl loginInteractor){
        return loginInteractor;
    }

    @ActivityScope
    @Provides
    public LoginContract.LoginPresenter providerLoginPresenter(LoginPresenterImpl loginPresenter) {
        return loginPresenter;
    }

}
