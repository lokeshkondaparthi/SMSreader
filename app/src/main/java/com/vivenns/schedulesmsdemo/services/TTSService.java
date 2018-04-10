package com.vivenns.schedulesmsdemo.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTSService extends Service implements TextToSpeech.OnInitListener {

    private String str;
    private TextToSpeech mTts;
    private static final String TAG = "TTSService";
    private AudioManager mAudioManager;

    @Override

    public IBinder onBind(Intent arg0) {

        return null;
    }


    @Override
    public void onCreate() {

        mTts = new TextToSpeech(this,
                this  // OnInitListener
        );
        mTts.setSpeechRate(0.5f);
        Log.v(TAG, "oncreate_service");
        str = "turn left please ";
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        // TODO Auto-generated method stub
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        mAudioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        sayHello(str);

        Log.v(TAG, "onstart_service");
        super.onStart(intent, startId);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sayHello(intent.getStringExtra("text"));
        return START_NOT_STICKY;
    }

    @Override
    public void onInit(int status) {
        Log.v(TAG, "oninit");
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.v(TAG, "Language is not available.");
            } else {

                sayHello(str);

            }
        } else {
            Log.v(TAG, "Could not initialize TextToSpeech.");
        }
    }

    private void sayHello(String str) {
        mTts.speak(str,
                TextToSpeech.QUEUE_FLUSH,
                null);
    }
}