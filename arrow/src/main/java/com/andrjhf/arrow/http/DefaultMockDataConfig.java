package com.andrjhf.arrow.http;

import android.app.Application;

import com.andrjhf.arrow.di.DataRepositoryModule;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 默认Mock数据的读取位置assets/mockdata/，mock数据的命名方式"网络Api名字+.json后缀"
 * 例如：loginUser.json
 * Created by jiahongfei on 2017/11/29.
 */

public class DefaultMockDataConfig implements DataRepositoryModule.MockDataConfig {
    @Override
    public String configMockDataConfig(Application context, Method method) {

        try {
            String mockData = "";
            String mockDataName = method.getName() + ".json";
            InputStream inputStream = context.getResources().getAssets().open("mockdata/" + mockDataName);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(inputStream.available());
            byte[] b = new byte[1024];
            int len = -1;
            while(-1 !=(len = inputStream.read(b))){
                byteArrayOutputStream.write(b,0,len);
            }
            byteArrayOutputStream.flush();
            mockData = byteArrayOutputStream.toString();
            byteArrayOutputStream.close();
            inputStream.close();
            return mockData;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
