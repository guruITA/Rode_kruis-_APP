package com.example.rodekruisapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FirstActivity : AppCompatActivity() {

    //huidige tijd
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var date: String
    lateinit var textView: TextView
    lateinit var button: Button

    //foto uploaden
    lateinit var pickIImageSwitcher: ImageSwitcher
    lateinit var previosBtn: Button
    lateinit var nextBtn: Button
    lateinit var pickImageBtn: Button

    private var images: ArrayList<Uri?>? = null
    private var position = 0
    private val PICK_IMAGES_CODE = 0

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

        //foto uploaden
        previosBtn = findViewById(R.id.previousBtn)
        nextBtn = findViewById(R.id.nextBtn)
        pickIImageSwitcher = findViewById(R.id.imageSwitcher)
        pickImageBtn = findViewById(R.id.pickImageBtn)

        images = ArrayList()

        pickIImageSwitcher.setFactory { ImageView(applicationContext) }

        pickImageBtn.setOnClickListener {
            pickImageIntent()

            if (previosBtn.isInvisible) {
                previosBtn.setVisibility(View.VISIBLE)
            } else {
                previosBtn.setVisibility(View.INVISIBLE)
            }

            if (nextBtn.isInvisible) {
                nextBtn.setVisibility(View.VISIBLE)
            } else {
                nextBtn.setVisibility(View.INVISIBLE)
            }
        }

        nextBtn.setOnClickListener {
            if (position < images!!.size - 1) {
                position++
                pickIImageSwitcher.setImageURI(images!![position])
            } else {
                Toast.makeText(this, "Geen foto's meer...", Toast.LENGTH_SHORT).show()
            }
        }

        previosBtn.setOnClickListener {
            if (position > 0) {
                position--
                pickIImageSwitcher.setImageURI(images!![position])
            } else {
                Toast.makeText(this, "Geen foto's meer...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pickImageIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select image(s)"), PICK_IMAGES_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGES_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                if (data!!.clipData != null) {
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        images!!.add(imageUri)
                    }
                    pickIImageSwitcher.setImageURI(images!![0])
                    position = 0
                } else {
                    val imageUri = data.data
                    pickIImageSwitcher.setImageURI(imageUri)
                    position = 0
                }
            }
        }
    }
}