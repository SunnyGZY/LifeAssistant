package com.gzy.lifeassistant.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.gzy.lifeassistant.R;

/**
 * 单词显示 widget
 *
 * @author gaozongyang
 * @date 2018/11/28
 */
public class WordWidgetProvider extends AppWidgetProvider {

    public static final String WORD_WIDGET_UPDATE = "com.gzy.lifeassistant.WORD_WIDGET_UPDATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (WordWidgetProvider.WORD_WIDGET_UPDATE.equals(intent.getAction())) {
            Toast.makeText(context, "on refreshing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider_word);
            Intent intent = new Intent(context, WordWidgetProvider.class);
            intent.setAction(WordWidgetProvider.WORD_WIDGET_UPDATE);
            remoteViews.setTextViewText(R.id.word_widget_word_text_view, "assign");
            remoteViews.setTextViewText(R.id.word_widget_phonetic_text_view, "英 [əˈsaɪn]  美 [əˈsaɪn]");
            remoteViews.setTextViewText(R.id.word_widget_paraphrase_text_view, "vt.分派，选派，分配；\n" +
                    "n.受托者；");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.word_widget_refresh_image_view, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}