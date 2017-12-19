package com.andrjhf.arrow.demo.login;

import android.util.Log;

import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.http.HttpDisposableObserver;
import com.andrjhf.arrow.http.IHttpErrorHandler;
import com.andrjhf.arrow.http.IHttpResponseHandler;
import com.andrjhf.arrow.mvp.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jiahongfei on 2017/11/24.
 */

public class LoginPresenterImpl extends BasePresenter<LoginContract.LoginInteractor, LoginContract.LoginView>
        implements LoginContract.LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";

    private LoginContract.LoginInteractor loginInteractor;
    private LoginContract.LoginView loginView;
    private IHttpResponseHandler iHttpResponseHandler;
    private IHttpErrorHandler iHttpErrorHandler;

    @Inject
    public LoginPresenterImpl(LoginContract.LoginView loginView,
                              LoginContract.LoginInteractor loginInteractor,
                              IHttpResponseHandler iHttpResponseHandler,
                              IHttpErrorHandler iHttpErrorHandler) {
        super(loginInteractor, loginView);
        this.loginInteractor = loginInteractor;
        this.loginView = loginView;
        this.iHttpResponseHandler = iHttpResponseHandler;
        this.iHttpErrorHandler = iHttpErrorHandler;
    }



    @Override
    public void start() {
        loginView.setLoginButtonText("登录");

    }

    @Override
    public void login(String phone, String code) {

        if (!phoneVerification(phone) || !codeVerification(code)) {
            return;
        }
        HttpDisposableObserver observer = new HttpDisposableObserver<ApiResponse<UserLogin>>(
                iHttpErrorHandler,
                iHttpResponseHandler
        ) {

            @Override
            protected void onStart() {
                loginView.showLoading();

            }

            @Override
            public void onResponse(@NonNull ApiResponse<UserLogin> apiResponse) {
                if (null != apiResponse && null != apiResponse.getData()) {
//                    loginView.userLogin(apiResponse.getData().getCode(),apiResponse.getData().getPhone());
                    loginView.userLogin("","");
                    loginView.setLoginButtonText("退出");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                super.onError(e);
                Log.e(TAG, "onError");
                loginView.onError();
            }

            @Override
            public void onFinish() {
                Log.e(TAG, "onFinish");
                loginView.hideLoading();

            }

            @Override
            public void onDispose() {
                Log.e(TAG, "onDispose");
            }
        };

        subscribe(loginInteractor.getUserLogin(phone, code, "blockBox", "extend"),observer);

//        ApiResponse<UserLogin> userLoginApiResponse = new ApiResponse<>();
//        userLoginApiResponse.setCode(1);
//        userLoginApiResponse.setMsg("成功");
//        UserLogin userLogin = new UserLogin();
//        userLogin.setCode("123456");
//        userLogin.setPhone("18701680736");
//        userLoginApiResponse.setData(userLogin);
//
//        Observable.just(userLoginApiResponse)
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.single())
//                .observeOn(Schedulers.single())
//                .subscribe(observer);
    }

    @Override
    public void logout() {

        new SubscribeBuilder()
                .setObservable(loginInteractor.logout("", ""))
                .setObserver(new HttpDisposableObserver<ApiResponse<Logout>>(
                        iHttpErrorHandler,
                        iHttpResponseHandler
                ) {

                    @Override
                    protected void onStart() {
                        loginView.showLoading();

                    }

                    @Override
                    public void onResponse(@NonNull ApiResponse<Logout> apiResponse) {
                        if (null != apiResponse && null != apiResponse.getData()) {
                            loginView.logout(apiResponse.getData());
                            loginView.setLoginButtonText("登录");
                        }
                    }

                    @Override
                    public void onFinish() {
                        loginView.hideLoading();

                    }

                })
                .subscribe();


    }

    private boolean phoneVerification(String phone) {
        if ("18701680736".equals(phone)) {
            return true;
        }
        return false;
    }

    private boolean codeVerification(String code) {
        if ("66666666".equals(code)) {
            return true;
        }
        return false;
    }
}
