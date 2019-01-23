package com.gzy.lifeassistant.ui.clock;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gzy.lifeassistant.R;
import com.gzy.lifeassistant.permission.PermissionUtil;
import com.gzy.lifeassistant.permission.RequestCallBack;

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
     * 显示AM PM
     */
    private TextView mMomentTextView;
    /**
     * 显示当前时间
     */
    private TextView mClockTextView;

    private TextView mLogTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        initView();
        startTiming();
        requestPermission();
    }

    private void requestPermission() {
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
        PermissionUtil.requestPermission(this, permissions, new RequestCallBack() {
            @Override
            public void granted() {
                startAudioRecord();
            }

            @Override
            public void denied() {
                Toast.makeText(ClockActivity.this, "未获取到麦克风权限，无法自动唤醒", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startAudioRecord() {
        AudioRecordDemo audioRecordDemo = new AudioRecordDemo(this);
        audioRecordDemo.getNoiseLevel();
    }

    private void initView() {
        mMomentTextView = findViewById(R.id.moment_text_view);
        mClockTextView = findViewById(R.id.clock_text_view);
        mLogTextView = findViewById(R.id.log_text_view);
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
    @SuppressLint("SetTextI18n")
    private void showCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int constantly = calendar.get(Calendar.AM_PM);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        if (constantly == 0) {
            mMomentTextView.setText("AM");
        } else {
            mMomentTextView.setText("PM");
        }

        String currentTime = hour + ":" + formatNumber(minute) + ":" + formatNumber(second);
        mClockTextView.setText(currentTime);
        Log.e("gzy", currentTime);
        handler.sendEmptyMessageDelayed(REFRESH_TIME, 1000);
    }

    /**
     * 格式化数字
     *
     * @param num 如果num为个位数，则在前面添加0
     * @return 格式化后的String
     */
    private String formatNumber(int num) {
        String string;
        if (num < 10) {
            string = "0" + num;
        } else {
            string = String.valueOf(num);
        }
        return string;
    }

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_TIME:
                    showCurrentTime();
                case 0x02:
                    Bundle bundle = msg.getData();
                    double volue = bundle.getDouble("VOLUME");
                    mLogTextView.setText(String.valueOf(volue));
                    if (volue >= 50) {
                        screenOn();
                    }
                default:
                    break;
            }
            return true;
        }
    });

    public static final String tag = "myapp:mywakelocktag";

    private void screenOn() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, tag);
            wl.acquire(10000);
            wl.release();
        }

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard();
    }


    public Handler getHandler() {
        return handler;
    }
}
