package com.tyeng.gatt

import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log

class TelephonyManagerHelper(context: Context) {

    companion object {
        val TAG = "mike_" + Throwable().stackTrace[2].className
    }

    private val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    private val phoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            when (state) {
                TelephonyManager.CALL_STATE_RINGING -> {
                    Log.i(TAG + Throwable().stackTrace[0].lineNumber, "Telephony: Incoming call from $phoneNumber")
                    // TODO: Handle incoming call
                }
                TelephonyManager.CALL_STATE_IDLE -> {
                    Log.i(TAG + Throwable().stackTrace[0].lineNumber, "Telephony: Call ended")
                    // TODO: Handle call ended
                }
                else -> {
                    // TODO: Handle other call states
                }
            }
        }
    }

    fun startListening() {
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        Log.i(TAG + Throwable().stackTrace[0].lineNumber, "Telephony: Listening for phone state changes")
    }

    fun stopListening() {
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE)
        Log.i(TAG + Throwable().stackTrace[0].lineNumber, "Telephony: Stopped listening for phone state changes")
    }
}
