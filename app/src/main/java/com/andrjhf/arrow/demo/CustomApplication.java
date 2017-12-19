package com.andrjhf.arrow.demo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.andrjhf.arrow.AppDelegateManager;
import com.andrjhf.arrow.di.AppComponent;
import com.andrjhf.arrow.di.AppDelegateConfig;
import com.andrjhf.arrow.di.DataRepositoryModule;
import com.andrjhf.arrow.http.DefaultHttpErrorHandler;
import com.andrjhf.arrow.http.DefaultHttpResponseHandler;
import com.andrjhf.arrow.mvp.IApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by jiahongfei on 2017/11/6.
 */

public class CustomApplication extends Application implements IApp {

    private static final String TAG = "CustomApplication";

    private static CustomApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        AppDelegateConfig appDelegateConfig = new AppDelegateConfig
                .Builder()
                .setBaseUrl("http://www.weather.com.cn/adat/sk/")
                .setCacheDir(getCacheDir())
                .setOkhttpConfig(new DataRepositoryModule.OkhttpConfig() {
                    @Override
                    public void configOkhttp(Application context, OkHttpClient.Builder builder) {
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                            @Override
                            public void log(String message) {
                                //打印retrofit日志
                                Log.e(TAG, "retrofit = " + message);
                            }
                        });
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addNetworkInterceptor(loggingInterceptor);
                    }
                })
//                .setMockDataConfig(new DataRepositoryModule.MockDataConfig() {
//                    @Override
//                    public String configMockDataConfig(Application context, Method method) {
//                        //读取Mock数据
//                        try {
//                            String mockData = "";
//                            String mockDataName = method.getName() + ".json";
//                            InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/mockdata/"+ mockDataName ));
//                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
//                            byte[] b = new byte[1024];
//                            int len = -1;
//                            while(-1 !=(len = inputStream.read(b))){
//                                byteArrayOutputStream.write(b,0,len);
//                            }
//                            byteArrayOutputStream.flush();
//                            mockData = byteArrayOutputStream.toString();
//                            byteArrayOutputStream.close();
//                            inputStream.close();
//
//                            Log.e(TAG,"setMockDataConfig: " + Thread.currentThread());
//
//                            return mockData;
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                })
//                .setIHttpErrorHandler(new DefaultHttpErrorHandler(this))
//                .setiHttpResponseHandler(new DefaultHttpResponseHandler())
                .builder();
        AppDelegateManager.getInstance().init(this, appDelegateConfig);
    }

    @Override
    public AppComponent getAppComponent() {
        return AppDelegateManager.getInstance().getAppComponent();
    }

    public static CustomApplication getInstance() {
        return instance;
    }

}
