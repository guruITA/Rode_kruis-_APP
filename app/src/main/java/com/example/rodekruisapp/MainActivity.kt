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

        //auto -> AutoActivity
        val auto: Button = findViewById(R.id.auto)
        auto.setOnClickListener {
            val intent = Intent(this, AutoActivity::class.java)
            startActivity(intent)
        }

        //ambulance -> AmbulanceActivity
        val ambulance: Button = findViewById(R.id.ambulance)
        ambulance.setOnClickListener {
            val intent = Intent(this, AmbulanceActivity::class.java)
            startActivity(intent)
        }

        //gator -> GatorActivity
        val gator: Button = findViewById(R.id.gator)
        gator.setOnClickListener {
            val intent = Intent(this, GatorActivity::class.java)
            startActivity(intent)
        }

        //quad -> QuadActivity
        val quad: Button = findViewById(R.id.quad)
        quad.setOnClickListener {
            val intent = Intent(this, QuadActivity::class.java)
            startActivity(intent)
        }

        //fiets -> FietsActivity
        val fiets: Button = findViewById(R.id.fiets)
        fiets.setOnClickListener {
            val intent = Intent(this, FietsActivity::class.java)
            startActivity(intent)
        }

        //aanhanger -> AanhangerActivity
        val aanhanger: Button = findViewById(R.id.aanhanger)
        aanhanger.setOnClickListener {
            val intent = Intent(this, AanhangerActivity::class.java)
            startActivity(intent)
        }

    }
}

