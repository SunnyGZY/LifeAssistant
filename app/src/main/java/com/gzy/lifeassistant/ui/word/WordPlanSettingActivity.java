package com.gzy.lifeassistant.ui.word;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gzy.lifeassistant.R;
import com.gzy.lifeassistant.utils.SpUtils;

/**
 * 计划设置界面
 *
 * @author gaozongyang
 * @date 2018/12/5
 */
public class WordPlanSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_plan_setting);

        initView();
    }

    private void initView() {
        mNumberEditText = findViewById(R.id.word_plan_number_edit_text);
        Button button = findViewById(R.id.word_plan_confirm_edit_text);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.word_plan_confirm_edit_text:
                savePlan();
                break;
            default:
                break;
        }
    }

    private void savePlan() {
        String string = mNumberEditText.getText().toString().trim();

        if (string.length() == 0) {
            return;
        }
        int number = Integer.valueOf(string);
        if (0 < number) {
            SpUtils.putInt(this, SpUtils.WORD_PLAN, number);
            finish();
        }
    }
}
