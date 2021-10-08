package com.example.minirobots.takePicture.infrastructure

import android.util.Log
import javax.inject.Inject

class MinirobotsLogger @Inject constructor() {
    fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}
