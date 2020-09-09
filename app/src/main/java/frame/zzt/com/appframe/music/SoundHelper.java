package frame.zzt.com.appframe.music;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;

import frame.zzt.com.appframe.MyApplication;

/**
 * 一个音频的工具类
 * Created by zeting
 * Date 18/12/5.
 */


public class SoundHelper {
    private static SoundHelper util;
    private SoundPool sp;
    private Context context = MyApplication.getInstance();
    private HashMap<String, Integer> spMap;
    private MediaPlayer mediaPlayer;

    synchronized public static SoundHelper getInstance() {

        if (util == null) {
            util = new SoundHelper();

        }
        return util;

    }

    private SoundHelper() {
        super();
        spMap = new HashMap<String, Integer>();
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        mediaPlayer = new MediaPlayer();
    }

    /**
     * 播放raw文件夹下的音频
     *
     * @param rawId
     * @param cycleNum
     */
    public void playSound(int rawId, int cycleNum) {
        spMap.put(rawId + "", sp.load(context, rawId, 1));
        playSp(rawId + "", cycleNum);

    }

    /**
     * 播放外部的音频
     *
     * @param url
     * @param cycleNum
     */
    public void playSound(String url, int cycleNum) {
        spMap.put(url, sp.load(url, 1));
        playSp(url, cycleNum);

    }

    /**
     * 通过SoundPool播放音频
     *
     * @param url
     * @param cycleNum
     */
    private void playSp(String url, int cycleNum) {
        AudioManager am = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = volumnCurrent / audioMaxVolumn;

        sp.play(spMap.get(url), volumnRatio, volumnRatio, 1, cycleNum, 1f);

    }

    /**
     * 暂停
     *
     * @param key
     */
    public void pauseSp(String key) {
        sp.pause(spMap.get(key));

    }

    /**
     * 停止
     *
     * @param key
     */
    public void stopSp(String key) {
        sp.stop(spMap.get(key));
    }

    /**
     * 播放本地或网络音频
     *
     * @param urlPath
     * @param isEarpiece 是否用耳麦播放
     * @param seek       初始进度
     */
    public void playMedia(String urlPath, boolean isEarpiece, int seek) {

        int streamType = AudioManager.STREAM_MUSIC;
        if (TextUtils.isEmpty(urlPath) || !new File(urlPath).exists()) {
            return;
        }

        if (isEarpiece) {
            streamType = AudioManager.STREAM_VOICE_CALL;
        }

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();

        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(streamType);
            mediaPlayer.setDataSource(urlPath);
            mediaPlayer.prepare();
            if (seek > 0) {
                mediaPlayer.seekTo(seek);
            }
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始或恢复
     */
    public void startMedia() {
        mediaPlayer.start();

    }

    /**
     * 得到mediaplayer
     *
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * 停止
     *
     * @return
     */
    public boolean stopMedia() {

        boolean result = false;
        try {
            mediaPlayer.stop();
            mediaPlayer.release();

            result = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();

            result = false;

        }

        return result;
    }

    /**
     * 暂停
     *
     * @return
     */
    public boolean pauseMedia() {

        boolean result = false;

        try {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            result = false;

        }

        return result;
    }

}