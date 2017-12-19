package com.andrjhf.arrow.demo;

import com.andrjhf.arrow.demo.login.LoginContract;
import com.andrjhf.arrow.demo.login.LoginInteractorImpl;
import com.andrjhf.arrow.demo.login.LoginPresenterImpl;
import com.andrjhf.arrow.demo.login.UserLogin;
import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.http.DefaultHttpResponseHandler;
import com.andrjhf.arrow.http.HttpDisposableObserver;
import com.andrjhf.arrow.http.IDataRepositoryManager;
import com.andrjhf.arrow.http.IHttpErrorHandler;
import com.andrjhf.arrow.http.IHttpResponseHandler;

import org.junit.Test;
import org.mockito.Matchers;

import java.util.Observer;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by jiahongfei on 2017/11/30.
 */

public class LoginPresenterImplTest {

    @Test
    public void testLogin() {
//        RxAndroidSchedulersHook rxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
//            @Override
//            public Scheduler getMainThreadScheduler() {
//                return Schedulers.immediate();
//            }
//        };
//
//        RxJavaSchedulersHook rxJavaSchedulersHook = new RxJavaSchedulersHook() {
//            @Override
//            public Scheduler getIOScheduler() {
//                return Schedulers.immediate();
//            }
//        };
//
//        RxAndroidPlugins.getInstance().registerSchedulersHook(rxAndroidSchedulersHook);
//        RxJavaPlugins.getInstance().registerSchedulersHook(rxJavaSchedulersHook);

//        RxAndroidPlugins.initMainThreadScheduler(new Callable<Scheduler>() {
//            @Override
//            public Scheduler call() throws Exception {
//                return Schedulers.single();
//            }
//        });
        asyncToSync();

        ServiceApi serviceApi = mock(ServiceApi.class);
        IDataRepositoryManager iDataRepositoryManager = mock(IDataRepositoryManager.class);
        when(iDataRepositoryManager.getRepositoryDataService(ServiceApi.class)).thenReturn(serviceApi);
        LoginContract.LoginInteractor loginInteractor = new LoginInteractorImpl(iDataRepositoryManager);
//        loginInteractor.getUserLogin("phone","code","blockBox","extendParam");
//        verify(iDataRepositoryManager).getRepositoryDataService(ServiceApi.class);
//        verify(serviceApi).getUserLogin("phone","code","blockBox","extendParam");

        IHttpResponseHandler iHttpResponseHandler = new DefaultHttpResponseHandler();
        IHttpErrorHandler iHttpErrorHandler = spy(IHttpErrorHandler.class);
        LoginContract.LoginView loginView = mock(LoginContract.LoginView.class);
        final ApiResponse<UserLogin> userLoginApiResponse = new ApiResponse<>();
        userLoginApiResponse.setCode(1);
        userLoginApiResponse.setMsg("成功");
        UserLogin userLogin = new UserLogin();
        userLogin.setCode("66666666");
        userLogin.setPhone("18701680736");
        userLoginApiResponse.setData(userLogin);
        when(loginInteractor.getUserLogin(anyString(), anyString(), anyString(), anyString())).thenReturn(Observable.just(userLoginApiResponse));
        LoginPresenterImpl loginPresenter = new LoginPresenterImpl(loginView, loginInteractor, iHttpResponseHandler, iHttpErrorHandler);
        loginPresenter.start();
        verify(loginView).setLoginButtonText("登录");
        loginPresenter.login("18701680736", "66666666");
//        verify(loginView).setLoginButtonText("退出");
//        verify(loginView).onError();
        verify(loginView).userLogin("","");
//        verify(loginView).finishNetwork();
//        verify(loginView).showToast(anyString());

//        verify(loginView).showLoading();
        verify(loginView,times(1)).hideLoading();
//        verify(loginView).showToast(anyString());
    }

    public static void asyncToSync() {

        RxJavaPlugins.reset();

        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
                                                @Override
                                                public Scheduler apply(@NonNull Scheduler scheduler) throws Exception {
                                                    return Schedulers.trampoline();
                                                }
                                            }
        );
    }
}
