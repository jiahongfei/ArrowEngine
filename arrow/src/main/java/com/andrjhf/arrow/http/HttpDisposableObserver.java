package com.andrjhf.arrow.http;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

/**
 * 自定义网络返回处理基类
 * void onStart()
 * void onResponse(@NonNull T t)
 * void onFinish()
 * void onDispose()
 * <p>
 * Created by jiahongfei on 2017/11/8.
 */

public abstract class HttpDisposableObserver<T extends ApiResponse>
        implements Observer<T>, Disposable {

    final AtomicReference<Disposable> s = new AtomicReference<>();

    private IHttpErrorHandler iHttpErrorHandler;
    private IHttpResponseHandler iHttpResponseHandler;

    public HttpDisposableObserver(IHttpErrorHandler iHttpErrorHandler,
                                  IHttpResponseHandler iHttpResponseHandler) {
        this.iHttpErrorHandler = iHttpErrorHandler;
        this.iHttpResponseHandler = iHttpResponseHandler;
    }

    public HttpDisposableObserver(IHttpErrorHandler iHttpErrorHandler) {
        this(iHttpErrorHandler, null);
    }

    public HttpDisposableObserver(IHttpResponseHandler iHttpResponseHandler) {
        this(null, iHttpResponseHandler);
    }


    public HttpDisposableObserver() {
        this(null, null);
    }

    @Override
    public final void onSubscribe(@NonNull Disposable s) {
        if (null != this.s && EndConsumerHelper.setOnce(this.s, s, getClass())) {
            onStart();
        }
    }

    @Override
    public final boolean isDisposed() {
        return s.get() == DisposableHelper.DISPOSED;
    }

    @Override
    public final void dispose() {
        onDispose();
        DisposableHelper.dispose(s);
    }

    @Override
    public void onNext(@NonNull T t) {
        boolean handler = true;
        if (null != iHttpResponseHandler) {
            handler = iHttpResponseHandler.httpResponseHandler(t);
        }
        if (handler) {
            onResponse(t);
        } else {
            HttpResponseHandlerException exception = new HttpResponseHandlerException(t.getCode(), t.getMsg());
            onError(exception);
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (null != iHttpErrorHandler) {
            iHttpErrorHandler.httpErrorHandler(e);
        }
        onFinish();
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    /**
     * Called once the single upstream Disposable is set via onSubscribe.
     */
    protected void onStart() {
    }

    public abstract void onResponse(@NonNull T t);

    /**
     * 正确，错误都会回调这个方法
     */
    public abstract void onFinish();

    /**
     * 多次调用网络，可能回调多次
     */
    public void onDispose() {
    }

}
