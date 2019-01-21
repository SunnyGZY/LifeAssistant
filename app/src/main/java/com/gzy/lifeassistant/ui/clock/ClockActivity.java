package com.gzy.lifeassistant.ui.clock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
     * 显示AM PM
     */
    private TextView mMomentTextView;
    /**
     * 显示当前时间
     */
    private TextView mClockTextView;

    private static final String TAG = "AudioRecord";
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord;
    boolean isGetVoiceRun = false;
    Object mLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        initView();
        startTiming();
        mLock = new Object();

        getNoiseLevel();
    }

    public void getNoiseLevel() {
        if (isGetVoiceRun) {
            Log.e(TAG, "还在录着呢");
            return;
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);

        isGetVoiceRun = true;

        // TODO: 2019/1/21 不要显式创建线程，请使用线程池
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);
                    Log.d(TAG, "分贝值:" + volume);
                    // 大概一秒十次
                    synchronized (mLock) {
                        try {
                            mLock.wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mAudioRecord.stop();
                        mAudioRecord.release();
                        mAudioRecord = null;
                    }
                }
            }
        }).run();
    }


    private void initView() {
        mMomentTextView = findViewById(R.id.moment_text_view);
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
                default:
                    break;
            }
            return true;
        }
    });
}
