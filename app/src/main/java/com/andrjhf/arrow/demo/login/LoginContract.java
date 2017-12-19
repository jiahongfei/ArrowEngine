package com.andrjhf.arrow.demo.login;

import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.mvp.IModel;
import com.andrjhf.arrow.mvp.IPresenter;
import com.andrjhf.arrow.mvp.IView;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * Created by jiahongfei on 16/8/2.
 */
public interface LoginContract {

    interface LoginView extends IView{

        void userLogin(String code, String phone);

        void logout(Logout logout);

        void setLoginButtonText(String text);

        void finishNetwork();

        void onError( );

    }

    interface LoginInteractor extends IModel{

        Observable<ApiResponse<UserLogin>> getUserLogin(
                String phone,
                String smsCode,
                String blockBox,
                String extendParam);

        Observable<ApiResponse<Logout>>  logout(
                    String phone,
                    String code);
    }

    interface LoginPresenter extends IPresenter {

        void start();

        void login(String phone, String code);

        void logout();
    }

}
