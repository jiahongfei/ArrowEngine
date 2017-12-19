package com.andrjhf.arrow.mvp;

import com.andrjhf.arrow.http.HttpDisposableObserver;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jiahongfei on 2017/11/6.
 */

public class BasePresenter<M extends IModel, V extends IView> {

    private static final String TAG = "BasePresenter";

    private static final CompositeDisposable disposables = new CompositeDisposable();

    protected M model;
    protected V view;

    public BasePresenter(M model, V view) {
        this.model = model;
        this.view = view;
    }

    /**
     * 发送接收网络的模板方法，网络在Schedulers.io()线程中，回调在AndroidSchedulers.mainThread()线程中
     * @param observable
     * @param observer
     * @param <T>
     * @param <K>
     */
    protected <T, K extends HttpDisposableObserver> void subscribe(Observable<T> observable, K observer) {

        registerDispose(observer);

        observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(observer);
    }

    /**
     * 持有Rxjava2的disposable，否则会造成内存泄漏
     *
     * @param disposable
     */
    protected static Disposable registerDispose(Disposable disposable) {
        if (disposables.add(disposable)) {
            return disposable;
        }
        return null;
    }

    private static void unRegisterDispose() {
        if (null != disposables) {
            disposables.clear();
        }
    }

    public void onDestory() {

        unRegisterDispose();

        if (null != this.model) {
            this.model.onDestory();
        }
    }

    /**
     * 发送网络请求，换了一种方式写，用链式表达式
     * @param <T>
     * @param <K>
     */
    public static final class  SubscribeBuilder<T,K extends HttpDisposableObserver>{

        private Observable<T> observable;
        private K observer;

        public SubscribeBuilder(){

        }

        public SubscribeBuilder setObservable(Observable<T> observable){
            this.observable = observable;
            return this;
        }

        public SubscribeBuilder setObserver(K observer){
            this.observer = observer;
            return this;
        }

        public void subscribe(){

            if(null == this.observable || null == this.observer){
                return ;
            }

            registerDispose(observer);

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }

    }
}
