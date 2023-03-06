package com.tyeng.gatt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var telephonyManagerHelper: TelephonyManagerHelper
    private lateinit var ttsHelper: TtsHelper
    private lateinit var sttHelper: SttHelper
    private lateinit var speechRecognizer: SpeechRecognizer

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 100
    }

    private val requiredPermissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (hasPermissions()) {
            initialize()
        } else {
            requestPermissions()
        }
    }

    private fun initialize() {
        telephonyManagerHelper = TelephonyManagerHelper(this)
        ttsHelper = TtsHelper(TextToSpeech(this, null))
        sttHelper = SttHelper(applicationContext)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)

        // TODO: Set up UI and handle call status, TTS events, and STT events
    }

    private fun hasPermissions(): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, requiredPermissions, REQUEST_CODE_PERMISSIONS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                initialize()
            } else {
                Log.e("MainActivity", "Required permissions not granted")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        telephonyManagerHelper.stopListening()
        ttsHelper.stop()
        ttsHelper.shutdown()
        sttHelper.stopListening()
        speechRecognizer.destroy()
    }
}
