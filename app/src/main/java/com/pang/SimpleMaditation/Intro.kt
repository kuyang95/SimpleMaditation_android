package com.pang.SimpleMaditation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Intro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro)

         var handler = Handler()
        handler.postDelayed(
                { var intent = Intent( this, MainActivity::class.java)
                    startActivity(intent) }
            , 3000) }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
