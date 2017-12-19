package com.andrjhf.arrow.mvp;

import com.andrjhf.arrow.http.IDataRepositoryManager;

import javax.inject.Inject;

/**
 * Created by jiahongfei on 2017/11/6.
 */

public class BaseModel {

    protected IDataRepositoryManager repositoryManager;

    public BaseModel(IDataRepositoryManager repositoryManager){
        this.repositoryManager = repositoryManager;
    }

    public void onDestory(){
        this.repositoryManager = null;
    }
}
