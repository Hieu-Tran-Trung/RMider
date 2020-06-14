package com.example.rmider.config;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

// Lớp này là lớp Application, nó là lớp chạy đầu tiên của mỗi ứng dụng, mỗi ứng dụng sẽ có 1 lớp này, nó sẽ chạy xuyên suốt theo ứng dụng
// Lớp này là lớp nâng cao
public class AppConfig extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static Context getContext(){
        return context;
    }
}
