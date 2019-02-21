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
import android.widget.TextView;
import android.widget.Toast;

import com.gzy.easypermission.PermissionUtil;
import com.gzy.easypermission.RequestCallBack;
import com.gzy.lifeassistant.R;
import com.gzy.lifeassistant.manager.AudioRecordManager;

import java.util.Calendar;

/**
 * 电子时钟
 *
 * @author gaozongyang
 * @date 2019/1/5
 */
public class ClockActivity extends Activity implements AudioRecordManager.AudioRecordCallBack {

    /**
     * 刷新显示的时间
     */
    private static final int REFRESH_SHOW_TIME = 0x01;
    /**
     * 接收到当前录音分贝
     */
    public static final int RECEIVE_VOLUME_DECIBEL = 0x02;

    public static final String VOLUME_DECIBEL = "volume_decibel";

    private static final int VOLUME_DECIBEL_TOPLIMIT = 50;

    public static final String TAG = "myapp:mywakelocktag";

    /**
     * 显示AM PM
     */
    private TextView mMomentTextView;
    /**
     * 显示当前时间
     */
    private TextView mClockTextView;

    private TextView mLogTextView;
    /**
     * 录音管理器
     */
    private AudioRecordManager mAudioRecordManager;

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_SHOW_TIME:
                    showCurrentTime();
                    break;
                case RECEIVE_VOLUME_DECIBEL:
                    Bundle bundle = msg.getData();
                    int volueDecibel = bundle.getInt(VOLUME_DECIBEL);
                    mLogTextView.setText(String.valueOf(volueDecibel));
                    if (volueDecibel >= VOLUME_DECIBEL_TOPLIMIT) {
                        screenOn();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        initView();
        startTiming();
        startAudioRecord();
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
        mHandler.sendEmptyMessage(REFRESH_SHOW_TIME);
    }

    private void startAudioRecord() {
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
        PermissionUtil.requestPermission(this, permissions, new RequestCallBack() {
            @Override
            public void granted() {
                mAudioRecordManager = new AudioRecordManager(ClockActivity.this);
                mAudioRecordManager.startRecord();
            }

            @Override
            public void denied() {
                Toast.makeText(ClockActivity.this, "未获取到麦克风权限，无法自动唤醒", Toast.LENGTH_SHORT).show();
            }
        });
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
        mHandler.sendEmptyMessageDelayed(REFRESH_SHOW_TIME, 1000);
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

    private void screenOn() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, TAG);
            wl.acquire(10000);
            wl.release();
        }

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard();
    }

    @Override
    public void volumeDecibel(int volumeDecibel) {
        Message message = new Message();
        message.what = RECEIVE_VOLUME_DECIBEL;
        Bundle bundle = new Bundle();
        bundle.putInt(VOLUME_DECIBEL, volumeDecibel);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAudioRecordManager.stopRecord();
    }
}
