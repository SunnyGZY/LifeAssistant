package com.gzy.lifeassistant.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.gzy.lifeassistant.R;
import com.gzy.lifeassistant.widget.WordWidgetProvider;

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
        ComponentName componentName = new ComponentName(UpdateWordWidgetService.this, WordWidgetProvider.class);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_provider_word);

        AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
        awm.updateAppWidget(componentName, remoteViews);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
