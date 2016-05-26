package com.sumavision.talktv4;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.jiongbull.jlog.JLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.sumavision.talktv4.BuildConfig;

/**
 *  desc  初始化Application
 *  @author  yangjh
 *  created at  16-5-18 下午4:37
 */
public class BaseApp extends Application {
    private RefWatcher refWatcher;
    private static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = (BaseApp) getApplicationContext();
        refWatcher = LeakCanary.install(this);
        Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build();
        JLog.init(this)
                .setDebug(BuildConfig.DEBUG);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApp application = (BaseApp) context.getApplicationContext();
        return application.refWatcher;
    }

    public static Context getContext() {
        return instance;
    }

}