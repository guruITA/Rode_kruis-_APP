package com.example.rodekruisapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ambulance -> firstActivity
        val auto: Button = findViewById(R.id.auto)
        auto.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }

        //ambulance -> secondActivity
        val ambulance: Button = findViewById(R.id.ambulance)
        ambulance.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        //gator -> thirdActivity
        val gator: Button = findViewById(R.id.gator)
        gator.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

    }
}

