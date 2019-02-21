package com.gzy.lifeassistant.manager;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * AudioRecordManager
 *
 * @author gaozongyang
 * @date 2019/2/21
 */
public class AudioRecordManager {

    private static final int SAMPLE_RATE_IN_HZ = 8000;

    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);

    private AudioRecord mAudioRecord;

    /**
     * 标记正在录音
     */
    private boolean isRecording = false;

    private final Object mLock;
    /**
     * 录音分贝回调
     */
    private AudioRecordCallBack mAudioRecordCallBack;

    public AudioRecordManager(AudioRecordCallBack audioRecordCallBack) {
        mLock = new Object();
        this.mAudioRecordCallBack = audioRecordCallBack;
    }

    public void startRecord() {
        if (isRecording) {
            throw new RuntimeException("重复启动");
        }
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);

        isRecording = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isRecording) {
                    //r是实际读取的数据长度，一般而言r会小于BUFFER_SIZE
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (short aBuffer : buffer) {
                        v += aBuffer * aBuffer;
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double decibel = 10 * Math.log10(mean);
                    if (mAudioRecordCallBack != null) {
                        mAudioRecordCallBack.volumeDecibel((int) decibel);
                    }

                    synchronized (mLock) {
                        try {
                            mLock.wait(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public void stopRecord() {
        isRecording = false;
        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;
    }

    public interface AudioRecordCallBack {

        /**
         * 返回录音分贝
         *
         * @param volumeDecibel 录音分贝
         */
        void volumeDecibel(int volumeDecibel);
    }
}