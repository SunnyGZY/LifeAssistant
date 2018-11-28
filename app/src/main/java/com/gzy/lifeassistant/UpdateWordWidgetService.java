package com.gzy.lifeassistant;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

/**
 * widget 更新服务
 *
 * @author gaozongyang
 * @date 2018/11/28
 */
public class UpdateWordWidgetService extends Service {

    public UpdateWordWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        int runningTaskCount = 1;
        ComponentName componentName = new ComponentName(UpdateWordWidgetService.this, WordWidgetProvider.class);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_provider_word);

//        remoteViews.setTextViewText(R.id., "正在运行软件:" + runningTaskCount);

        AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
        awm.updateAppWidget(componentName, remoteViews);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
