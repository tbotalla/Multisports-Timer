package com.example.tbotalla.multisportstimer;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by tbotalla on 11/01/16.
 */
public class SoundManager {

    private SoundPool soundPool;
    private Context pContext;
    private float rate = 1.0f;
    private float leftVolume = 1.0f;
    private float rightVolume = 1.0f;


    public SoundManager(Context appContext){
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
        pContext = appContext;
    }


    public int load(int idSound) {
        return soundPool.load(pContext, idSound, 1);

    }


    public void play(final int idSonido) {
        // Wait for the sound to complete loading
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(idSonido, leftVolume, rightVolume, 1, 0, rate);
            }
        });
    }

    public void unloadAll() {
        soundPool.release();
    }
}
