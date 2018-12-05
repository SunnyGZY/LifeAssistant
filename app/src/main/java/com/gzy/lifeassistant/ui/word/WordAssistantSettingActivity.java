package com.gzy.lifeassistant.ui.word;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gzy.lifeassistant.R;

/**
 * 单词助手设置界面
 *
 * @author gaozongyang
 * @date 2018/12/5
 */
public class WordAssistantSettingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_assistant_setting);

        initView();
    }

    private void initView() {
        TextView mListTv = findViewById(R.id.setting_word_list_text_view);
        mListTv.setOnClickListener(this);
        TextView mPlanTv = findViewById(R.id.setting_word_plan_text_view);
        mPlanTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_word_list_text_view:
                startActivity(new Intent(this, WordListActivity.class));
                break;
            case R.id.setting_word_plan_text_view:
                startActivity(new Intent(this, WordPlanSettingActivity.class));
                break;
            default:
                break;
        }
    }
}
