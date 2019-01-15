package com.gzy.lifeassistant.ui.clock;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.TextView;

import com.gzy.lifeassistant.R;

import java.util.Calendar;

/**
 * 电子时钟
 *
 * @author gaozongyang
 * @date 2019/1/5
 */
public class ClockActivity extends Activity {

    /**
     * 刷新时间
     */
    private static final int REFRESH_TIME = 0x01;

    /**
     * 显示当前时间
     */
    private TextView mClockTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        initView();
        startTiming();
    }

    private void initView() {
        // 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mClockTextView = findViewById(R.id.clock_text_view);
    }

    /**
     * 开始计时
     */
    private void startTiming() {
        handler.sendEmptyMessage(REFRESH_TIME);
    }

    /**
     * 显示当前时间
     */
    private void showCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String currentTime;
        if (second <= 10) {
            currentTime = hour + ":" + minute + ":0" + second;
        } else {
            currentTime = hour + ":" + minute + ":" + second;
        }
        mClockTextView.setText(currentTime);
        handler.sendEmptyMessageDelayed(REFRESH_TIME, 1000);
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_TIME:
                    showCurrentTime();
                default:
                    break;
            }
            return true;
        }
    });
}
