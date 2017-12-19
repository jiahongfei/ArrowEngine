package com.andrjhf.arrow.demo;

import com.andrjhf.arrow.demo.login.LoginContract;
import com.andrjhf.arrow.demo.login.LoginInteractorImpl;
import com.andrjhf.arrow.http.IDataRepositoryManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyString;

/**
 * Created by jiahongfei on 2017/12/1.
 */

public class LoginInteractorImplTest {

    private ServiceApi serviceApi;
    private LoginContract.LoginInteractor loginInteractor;

    @Before
    public void setup(){

        IDataRepositoryManager iDataRepositoryManager = Mockito.mock(IDataRepositoryManager.class);
        serviceApi = Mockito.mock(ServiceApi.class);
        Mockito.when(iDataRepositoryManager.getRepositoryDataService(ServiceApi.class)).thenReturn(serviceApi);
        loginInteractor = new LoginInteractorImpl(iDataRepositoryManager);

    }

    @Test
    public void testGetUserLogin() {
        loginInteractor.getUserLogin("phone","code","block","extend");
        Mockito.verify(serviceApi).getUserLogin(anyString(),anyString(),anyString(),anyString());
    }

    @Test
    public void testLogout() {
        loginInteractor.logout("phone","code");
        Mockito.verify(serviceApi).logout(anyString(),anyString(),anyString(),anyString());

    }

}
