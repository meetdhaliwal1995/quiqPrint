package com.quiqprint.hub_android.di

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(private var context: Context) {


    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(SINGLE_IMAGE, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()


    var singleImageData : String?
        get() = sharedPreferences.getString(SINGLE_IMAGE, "")
        set(value) {
            editor.putString(SINGLE_IMAGE, value)
            editor.commit()
        }

    companion object {

        private var instance: SharedPreferences? = null

        const val SINGLE_IMAGE = "single_image"
    }
}