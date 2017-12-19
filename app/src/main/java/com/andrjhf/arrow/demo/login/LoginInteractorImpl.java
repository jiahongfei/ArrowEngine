package com.andrjhf.arrow.demo.login;

import com.andrjhf.arrow.demo.ServiceApi;
import com.andrjhf.arrow.di.MockData;
import com.andrjhf.arrow.http.ApiResponse;
import com.andrjhf.arrow.http.IDataRepositoryManager;
import com.andrjhf.arrow.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by jiahongfei on 2017/11/24.
 */

public class LoginInteractorImpl extends BaseModel implements LoginContract.LoginInteractor {

    private ServiceApi serviceApi;

    @Inject
    public LoginInteractorImpl(@MockData IDataRepositoryManager repositoryManager) {
        super(repositoryManager);
        serviceApi = repositoryManager.getRepositoryDataService(ServiceApi.class);
    }


    @Override
    public Observable<ApiResponse<UserLogin>> getUserLogin(String phone, String smsCode, String blockBox, String extendParam) {

        return serviceApi.getUserLogin(phone,smsCode,blockBox,extendParam);

    }

    @Override
    public Observable<ApiResponse<Logout>> logout(String phone, String code) {
        return serviceApi.logout("",phone,code,"");
    }
}
