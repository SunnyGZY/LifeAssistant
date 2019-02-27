package com.gzy.lifeassistant;

import android.annotation.SuppressLint;
import android.app.Application;

import com.taobao.sophix.SophixManager;

/**
 * App
 *
 * @author gaozongyang
 * @date 2018/12/3
 */
@SuppressLint("Registered")
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 检查热更新
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    /**
     * 外部获取单例
     *
     * @return Application
     */
    public static App getInstance() {
        return instance;
    }
}
