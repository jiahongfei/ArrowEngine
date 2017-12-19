package com.andrjhf.arrow.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.andrjhf.arrow.di.AppComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiahongfei on 2017/11/6.
 */
public class BaseActivity<T extends IPresenter> extends AppCompatActivity {

    protected AppComponent appComponent;

    @Inject
    protected T mPresenter;

    private Unbinder unbinder;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appComponent = ((IApp)getApplication()).getAppComponent();
    }

    @Override
    protected void onDestroy() {
        if(null != mPresenter){
            mPresenter.onDestory();
        }
        if(null != unbinder){
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }
}
