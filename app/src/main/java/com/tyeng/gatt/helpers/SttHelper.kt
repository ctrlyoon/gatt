package com.tyeng.gatt

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log

class SttHelper(private val applicationContext: Context) {

    private var recognizer: SpeechRecognizer? = null

    companion object {
        val TAG = "mike_" + Throwable().stackTrace[2].className
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        recognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
        recognizer?.setRecognitionListener(object : SimpleRecognitionListener() {
            override fun onResults(results: List<String>) {
                Log.i(TAG + Throwable().stackTrace[0].lineNumber, "STT: Recognized text = ${results[0]}")
                // TODO: Handle recognized text
            }

            override fun onError(error: Int) {
                Log.i(TAG + Throwable().stackTrace[0].lineNumber, "STT: Error code = $error")
                // TODO: Handle error
            }
        })
        recognizer?.startListening(intent)
        Log.i(TAG + Throwable().stackTrace[0].lineNumber, "STT: Listening for speech")
    }

    fun stopListening() {
        recognizer?.stopListening()
        recognizer?.destroy()
        recognizer = null
        Log.i(TAG + Throwable().stackTrace[0].lineNumber, "STT: Stopped listening for speech")
    }
}
