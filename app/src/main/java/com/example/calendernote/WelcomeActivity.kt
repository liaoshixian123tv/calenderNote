package com.example.calendernote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.calendernote.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivNote.alpha =0f
        binding.ivNote.animate().setDuration(1500).alpha(1f).withEndAction { navToHomePage() }
    }

    // navToHomePage 跳轉 起始頁面 至 主頁面
    private fun navToHomePage(){
       startActivity(Intent(this,MainActivity::class.java))
       overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
       finish()
    }
}


