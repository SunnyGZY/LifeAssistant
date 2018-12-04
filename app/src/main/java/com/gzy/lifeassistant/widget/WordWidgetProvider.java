package com.gzy.lifeassistant.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.gzy.lifeassistant.App;
import com.gzy.lifeassistant.Global;
import com.gzy.lifeassistant.R;
import com.gzy.lifeassistant.model.db.DaoSession;
import com.gzy.lifeassistant.model.db.WordBean;
import com.gzy.lifeassistant.model.db.WordBeanDao;
import com.gzy.lifeassistant.utils.SpUtils;

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
        if (WordWidgetProvider.WORD_WIDGET_UPDATE.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                AppWidgetManager appWidgetManger = AppWidgetManager
                        .getInstance(context);
                refreshView(context, appWidgetManger);
            }
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        refreshView(context, appWidgetManager);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
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

    private void refreshView(Context context, AppWidgetManager appWidgetManager) {
        int wordIndex = SpUtils.getInt(context, Global.Sp.CURRENT_WORD_INDEX, 1) % 50;
        DaoSession daoSession = App.getInstance().getDaoSession();
        WordBeanDao wordBeanDao = daoSession.getWordBeanDao();
        WordBean wordBean = wordBeanDao.queryBuilder()
                .where(WordBeanDao.Properties.Id.eq(wordIndex))
                .unique();
        if (wordBean == null) {
            return;
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider_word);
        remoteViews.setTextViewText(R.id.word_widget_word_text_view, wordBean.getWord());
        remoteViews.setTextViewText(R.id.word_widget_phonetic_text_view, wordBean.getPhonetic());
        remoteViews.setTextViewText(R.id.word_widget_paraphrase_text_view, wordBean.getTrans());
        remoteViews.setTextViewText(R.id.word_widget_tag_text_view, wordBean.getTags());
        Intent intent = new Intent(context, WordWidgetProvider.class);
        intent.setAction(WordWidgetProvider.WORD_WIDGET_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.word_widget_refresh_image_view, pendingIntent);
        appWidgetManager.updateAppWidget(new ComponentName(context, WordWidgetProvider.class), remoteViews);

        SpUtils.putInt(context, Global.Sp.CURRENT_WORD_INDEX, ++wordIndex);
    }
}