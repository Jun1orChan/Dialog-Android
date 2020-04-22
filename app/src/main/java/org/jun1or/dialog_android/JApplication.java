package org.jun1or.dialog_android;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class JApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
//        LeakCanary.enableDisplayLeakActivity(this);
    }
}
