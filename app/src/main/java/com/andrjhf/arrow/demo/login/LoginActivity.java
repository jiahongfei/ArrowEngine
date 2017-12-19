package com.andrjhf.arrow.demo.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andrjhf.arrow.demo.R;
import com.andrjhf.arrow.mvp.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册、登录
 */
public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginContract.LoginView {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.edt_input_phone_number)
    protected EditText mInputPhoneNumberEditText;
    @BindView(R.id.edt_input_code)
    protected EditText mInputCodeEditText;
    @BindView(R.id.btn_login)
    protected Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);

        mPresenter.start();
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:{
                if("登录".equals(mLoginButton.getText().toString())){
                    mPresenter.login(mInputPhoneNumberEditText.getText().toString(),mInputCodeEditText.getText().toString());

                }else{
                    mPresenter.logout();
                }
                break;
            }
            default:break;
        }
    }

    @Override
    public void showLoading() {
        Log.e(TAG,"showLoading : " + Thread.currentThread());
    }

    @Override
    public void hideLoading() {
        Log.e(TAG,"hideLoading: " + Thread.currentThread());

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void userLogin(String code, String phone) {
        Log.e(TAG,"userLogin : " + code);

    }

    @Override
    public void logout(Logout logout) {
        Log.e(TAG,"logout : " + logout.toString());

    }

    @Override
    public void setLoginButtonText(String text) {
        mLoginButton.setText(text);
    }

    @Override
    public void finishNetwork() {
        Log.e(TAG,"finishNetwork");

    }

    @Override
    public void onError() {

    }
}
