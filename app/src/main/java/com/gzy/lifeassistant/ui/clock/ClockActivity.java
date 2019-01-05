package com.gzy.lifeassistant.ui.clock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gzy.lifeassistant.R;

/**
 * 电子时钟
 *
 * @author gaozongyang
 * @date 2019/1/5
 */
public class ClockActivity extends AppCompatActivity {

    /**
     * 显示当前时间
     */
    private TextView mClockTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        initView();
    }

    private void initView() {
        mClockTextView = findViewById(R.id.clock_text_view);
        mClockTextView.setText("07:20");
    }
}
