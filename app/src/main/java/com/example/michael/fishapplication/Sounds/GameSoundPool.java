package com.example.michael.fishapplication.Sounds;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.michael.fishapplication.Activity.MainActivity;
import com.example.michael.fishapplication.R;

import java.util.HashMap;

/**
 * Created by Michael on 2016/11/21.
 */
public class GameSoundPool {
    private MainActivity mainActivity;
    private SoundPool soundPool;
    private HashMap<Integer,Integer> map;
    public GameSoundPool(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        map = new HashMap<Integer,Integer>();
        soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC,0);
    }
    public void initGameSound(){
        map.put(1, soundPool.load(mainActivity, R.raw.start, 1));
        map.put(2, soundPool.load(mainActivity, R.raw.die2, 1));
        map.put(3, soundPool.load(mainActivity, R.raw.eat, 1));
        map.put(4, soundPool.load(mainActivity, R.raw.lang, 1));
        map.put(5, soundPool.load(mainActivity, R.raw.upgrade, 1));
//        map.put(6, soundPool.load(mainActivity, R.raw.get_goods, 1));
//        map.put(7, soundPool.load(mainActivity, R.raw.button, 1));
    }
    //播放音效
    public void playSound(int sound,int loop){
        AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
        float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
        soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
    }
    public void stop(int sound){
        soundPool.stop(map.get(sound));
    }
}
