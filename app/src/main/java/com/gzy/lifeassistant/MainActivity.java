package com.gzy.lifeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gzy.lifeassistant.model.WordBookBean;
import com.gzy.lifeassistant.model.db.DaoSession;
import com.gzy.lifeassistant.model.db.WordBean;
import com.gzy.lifeassistant.model.db.WordBeanDao;
import com.gzy.lifeassistant.ui.clock.ClockActivity;
import com.gzy.lifeassistant.ui.word.WordAssistantSettingActivity;
import com.gzy.lifeassistant.utils.AssetsUtils;

import java.lang.reflect.Type;

/**
 * MainActivity
 *
 * @author gaozongyang
 * @date 2018/11/28
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mWordAssistantTextView;
    private TextView mClockTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        mWordAssistantTextView = findViewById(R.id.main_word_assistant_text_view);
        mWordAssistantTextView.setOnClickListener(this);
        mClockTextView = findViewById(R.id.main_clock_text_view);
        mClockTextView.setOnClickListener(this);
    }

    private void initData() {
        initWordData();
    }

    private void initWordData() {
        String jsonString = AssetsUtils.getString("cet_4_word_database.json", MainActivity.this);
        Type listType = new TypeToken<WordBookBean>() {
        }.getType();
        WordBookBean wordBookBean = new Gson().fromJson(jsonString, listType);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        WordBeanDao wordBeanDao = daoSession.getWordBeanDao();
        long wordBeanCount = wordBeanDao.queryBuilder().count();
        if (wordBeanCount != wordBookBean.getWordbook().getItem().size()) {
            if (wordBeanCount != 0) {
                wordBeanDao.deleteAll();
            }
            for (WordBean wordBean : wordBookBean.getWordbook().getItem()) {
                daoSession.insert(wordBean);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_word_assistant_text_view:
                startActivity(new Intent(this, WordAssistantSettingActivity.class));
                break;
            case R.id.main_clock_text_view:
                startActivity(new Intent(this, ClockActivity.class));
                break;
            default:
                break;
        }
    }
}
