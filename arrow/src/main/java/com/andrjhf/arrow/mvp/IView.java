package com.andrjhf.arrow.mvp;

/**
 * Created by jiahongfei on 2017/11/3.
 */

public interface IView {

    void showLoading();
    void hideLoading();

    void showToast(String message);
}
