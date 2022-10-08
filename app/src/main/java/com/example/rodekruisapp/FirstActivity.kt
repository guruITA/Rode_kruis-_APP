package com.example.rodekruisapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class FirstActivity : AppCompatActivity() {

    //huidige tijd
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var date: String
    lateinit var textView: TextView
    lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Auto"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //huidige tijd
        textView = findViewById(R.id.datum)
        button = findViewById(R.id.btndatum)
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        date = simpleDateFormat.format(calendar.time)
        button.setOnClickListener { textView.text = date }
    }
}