package com.apptutti.tuttistore.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.apptutti.tuttistore.utils.AppUtils;
import com.apptutti.tuttistore.utils.CrashHandler;
import com.apptutti.tuttistore.utils.LogUtils;
import com.apptutti.tuttistore.utils.PrefsUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 文强  作者 E-mail:   283006603@qq.com
 * @date 创建时间：2017/8/28 11:27
 * 描述:APP
 * #                                                   #
 */
public final class BaseApplication extends Application{
    private static BaseApplication mContext;
    private Set<Activity> allActivities;
    /*private AppComponent mAppComponent;*/

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        mContext = this;
        initNetwork();
        /*initStetho();*/
        initCrashHandler();
        initLog();
        initPrefs();
       /* initComponent();*/

        /*initRouter(this);*/
    }

    /**
     * 初始化 ARouter
     *
     * @param
     */
   /* private void initRouter(BaseApplication baseApplication) {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(baseApplication); // 尽可能早，推荐在Application中初始化
    }*/
    private boolean isDebug() {
        return true;
    }



    /**
     * 增加Activity
     *
     * @param act act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除Activity
     *
     * @param act act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
/*
    *//**
     * 初始化网络模块组件
     *//*
    private void initComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .apiModule(new HttpMethods())
                .appModule(new AppModule(this))
                .build();

    }*/

    /*public AppComponent getAppComponent() {
        return mAppComponent;
    }*/

    /**
     * 初始化sp
     */
    private void initPrefs() {
        PrefsUtils.init(this, getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    /**
     * 初始化调试
     */
   /* private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }*/

    /**
     * 开启网络监听
     */
    private void initNetwork() {
//        NetworkUtils.startNetService(this);
        Log.d("BaseApplication", "Appication initNetwork");

    }

    /**
     * 获取Application
     *
     * @return BiliCopyApplication
     */
    public static BaseApplication getInstance() {
        return mContext;
    }


    /**
     * 初始化崩溃日志
     */
    private void initCrashHandler() {
        CrashHandler.getInstance().init(this);
    }


    /**
     * 初始化log
     */
    private void initLog() {
        LogUtils.init(this);
    }
}
