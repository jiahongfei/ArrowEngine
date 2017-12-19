package com.andrjhf.arrow.http;

import android.app.Application;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.andrjhf.arrow.di.DataRepositoryModule;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Mock本地测试数据的类
 * Created by jiahongfei on 2017/11/27.
 */

public class MockDataRespsitoryManager implements IDataRepositoryManager {

    private static final String TAG = "MockDataManager";

    private DataRepositoryModule.MockDataConfig mMockDataConfig;
    private Application mApplication;

    @Inject
    public MockDataRespsitoryManager(Application application, @Nullable DataRepositoryModule.MockDataConfig mockDataConfig) {
        this.mMockDataConfig = mockDataConfig;
        this.mApplication = application;
    }

    @Override
    public <T> T getRepositoryDataService(Class<T> tClass) {
        return create(tClass);
    }

    @Override
    public <T> T getRepositoryDataProvider(Class<T> tClass) {
        return create(tClass);
    }


    public <T> T create(final Class<T> service) {
        validateServiceInterface(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }
                        Type returnType = method.getGenericReturnType();
                        Type observableType = get(returnType);

                        String mockData = getMockData(method);

                        return mockObservable(observableType, mockData);
                    }
                });
    }

    private String getMockData(Method method) {
        String mockData = "{\"code\":2,\"msg\":\"失败,请设置DataRepositoryModule.MockDataConfig\",  \"data\":{}}";

        if (null != mMockDataConfig) {
            mockData = mMockDataConfig.configMockDataConfig(mApplication, method);
        }

        if(TextUtils.isEmpty(mockData)){
            mockData = "{\"code\":2,\"msg\":\"失败,MockData为空\",  \"data\":{}}";

        }

        return mockData;
    }

    private <K> Observable<K> mockObservable(final Type type, String s) {

        return Observable.just(s).flatMap(new Function<String, ObservableSource<K>>() {
            @Override
            public ObservableSource<K> apply(@NonNull String s) throws Exception {

                TimeUnit.MILLISECONDS.sleep(300);

                K apiResponse = JSON.parseObject(s, type);

                return Observable.just(apiResponse);
            }
        });
    }

    public Type get(Type returnType) {

        if (!(returnType instanceof ParameterizedType)) {
            String name = "Observable";
            throw new IllegalStateException(name + " return type must be parameterized"
                    + " as " + name + "<Foo> or " + name + "<? extends Foo>");
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType == ApiResponse.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("ApiResponse must be parameterized"
                        + " as ApiResponse<Foo> or ApiResponse<? extends Foo>");
            }
            return observableType;
        }
        return null;
    }

    private <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        // Prevent API interfaces from extending other interfaces. This not only avoids a bug in
        // Android (http://b.android.com/58753) but it forces composition of API declarations which is
        // the recommended pattern.
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    private Type getParameterUpperBound(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException(
                    "Index " + index + " not in range [0," + types.length + ") for " + type);
        }
        Type paramType = types[index];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

    private Class<?> getRawType(Type type) {
        checkNotNull(type, "type == null");

        if (type instanceof Class<?>) {
            // Type is a normal class.
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
            // suspects some pathological case related to nested classes exists.
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
            // type that's more general than necessary is okay.
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }


}
