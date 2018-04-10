package com.vivenns.schedulesmsdemo;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class SpeakUtility implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;



    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
