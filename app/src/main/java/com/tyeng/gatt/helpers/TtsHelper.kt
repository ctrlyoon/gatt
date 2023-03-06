package com.tyeng.gatt

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log


class TtsHelper(private val tts: TextToSpeech) {

    companion object {
        val TAG = "mike_" + Throwable().stackTrace[2].className
    }

    private val utteranceId = "greeting"

    fun speak(message: String) {
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String?) {
                Log.i(TAG + Throwable().stackTrace[0].lineNumber, "TTS: Speech finished for utteranceId: $utteranceId")
            }

            override fun onError(utteranceId: String?) {
                Log.i(TAG + Throwable().stackTrace[0].lineNumber, "TTS: Error occurred while speaking for utteranceId: $utteranceId")
            }

            override fun onStart(utteranceId: String?) {
                Log.i(TAG + Throwable().stackTrace[0].lineNumber, "TTS: Speech started for utteranceId: $utteranceId")
            }
        })

        val params = Bundle()
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)

        tts.speak(message, TextToSpeech.QUEUE_FLUSH, params, utteranceId)
    }


    fun stop() {
        tts.stop()
        Log.i(TAG, "TTS: Speech stopped")
    }

    fun shutdown() {
        tts.shutdown()
        Log.i(TAG, "TTS: Speech shutdown")
    }
}

