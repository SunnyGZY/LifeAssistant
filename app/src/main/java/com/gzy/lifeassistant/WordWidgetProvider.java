package com.gzy.lifeassistant;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 单词显示 widget
 *
 * @author gaozongyang
 * @date 2018/11/28
 */
public class WordWidgetProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";

//https://www.cnblogs.com/liyiran/p/5268117.html

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Intent stopUpdateIntent = new Intent(context, UpdateWordWidgetService.class);
        context.stopService(stopUpdateIntent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Intent startUpdateIntent = new Intent(context, UpdateWordWidgetService.class);
        context.startService(startUpdateIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TOAST_ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "Touched view", Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);

        Intent startUpdateIntent = new Intent(context, UpdateWordWidgetService.class);
        context.startService(startUpdateIntent);
    }
}