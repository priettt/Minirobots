package com.example.minirobots

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.minirobots.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point into our app. This app follows the single-activity pattern, and all
 * functionality is implemented in the form of fragments.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        container = binding.fragmentContainer
        setContentView(binding.root)
    }
}