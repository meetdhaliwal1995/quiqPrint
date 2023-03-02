package com.quiqprint.hub_android

import android.app.Application
import com.google.gson.Gson

class MyApplication: Application() {

    companion object {
        /** Returns singleton instance of MyApplication  */
        @JvmStatic
        @Volatile
        lateinit var instance: MyApplication
            private set

        lateinit var gson: Gson

        var isVideoPlayerMuted: Boolean = false

        var referrerId: String = ""
    }

}