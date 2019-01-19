package com.gzy.lifeassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gzy.lifeassistant.ui.clock.ClockActivity;

/**
 * MainActivity
 *
 * @author gaozongyang
 * @date 2018/11/28
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mClockTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        mClockTextView = findViewById(R.id.main_clock_text_view);
        mClockTextView.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_clock_text_view:
                startActivity(new Intent(this, ClockActivity.class));
                break;
            default:
                break;
        }
    }
}
