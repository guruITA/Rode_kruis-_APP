package com.example.rodekruisapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //auto -> firstActivity
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

        val quad: Button = findViewById(R.id.quad)
        quad.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

        val fiets: Button = findViewById(R.id.fiets)
        fiets.setOnClickListener {
            val intent = Intent(this, FifthActivity::class.java)
            startActivity(intent)
        }

        val aanhanger: Button = findViewById(R.id.aanhanger)
        aanhanger.setOnClickListener {
            val intent = Intent(this, SixthActivity::class.java)
            startActivity(intent)
        }

    }
}

