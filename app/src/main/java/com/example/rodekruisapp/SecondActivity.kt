//Ambulance

package com.example.rodekruisapp

import android.app.Activity
import android.app.AlertDialog
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

class SecondActivity : AppCompatActivity() {

    //Edit text kan niet leeg zijn
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    lateinit var kenteken: EditText

    //huidige tijd
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var date: String
    lateinit var datum: TextView
    lateinit var button: Button

    //foto uploaden
    lateinit var pickIImageSwitcher: ImageSwitcher
    lateinit var previosBtn: Button
    lateinit var nextBtn: Button
    lateinit var pickImageBtn: Button

    private var images: ArrayList<Uri?>? = null
    private var position = 0
    private val PICK_IMAGES_CODE = 0

    //Voor AVG check
    lateinit var verstuur: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Ambulance"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Edit text kan niet leeg zijn
        naamGebruiker = findViewById(R.id.naamGebruiker)
        controleur = findViewById(R.id.controleur)
        kenteken = findViewById(R.id.kenteken)

        //huidige tijd
        datum = findViewById(R.id.datum)
        button = findViewById(R.id.btndatum)
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        date = simpleDateFormat.format(calendar.time)
        button.setOnClickListener { datum.text = date }

        //foto uploaden
        previosBtn = findViewById(R.id.previousBtn)
        nextBtn = findViewById(R.id.nextBtn)
        pickIImageSwitcher = findViewById(R.id.imageSwitcher)
        pickImageBtn = findViewById(R.id.pickImageBtn)

        images = ArrayList()

        pickIImageSwitcher.setFactory { ImageView(applicationContext) }

        pickImageBtn.setOnClickListener {
            pickImageIntent()
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

        //Voor AVG check
        verstuur = findViewById(R.id.verstuur)
        var builder = AlertDialog.Builder(this)

        verstuur.setOnClickListener {
            if (naamGebruiker.text.toString().trim().isEmpty() ||
                controleur.text.toString().trim().isEmpty() ||
                kenteken.text.toString().trim().isEmpty() ||
                datum.text.toString().trim().isEmpty()
            ) {
                if (naamGebruiker.text.toString().trim().isEmpty()) {
                    naamGebruiker.error = "Verplicht"
                }
                if (controleur.text.toString().trim().isEmpty()) {
                    controleur.error = "Verplicht"
                }
                if (kenteken.text.toString().trim().isEmpty()) {
                    kenteken.error = "Verplicht"
                }
            } else {

                //Voor AVG check
                builder.setTitle("Toestemming verwerken persoongegevens")
                    .setMessage("Uw gegevens worden 3 maanden in ons systeem bewaard, gaat u hiermee akkoord?")
                    .setCancelable(true)
                    .setPositiveButton("Ja") { dialogInterface, it ->
                        finish()
                    }
                    .setNegativeButton("Nee") { dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()

            }
        }
    }

    //Foto uploaden
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

                    if (previosBtn.isInvisible) {
                        previosBtn.visibility = View.VISIBLE
                    } else {
                        previosBtn.visibility = View.INVISIBLE
                    }

                    if (nextBtn.isInvisible) {
                        nextBtn.visibility = View.VISIBLE
                    } else {
                        nextBtn.visibility = View.INVISIBLE
                    }
                } else {
                    val imageUri = data.data
                    pickIImageSwitcher.setImageURI(imageUri)
                    position = 0
                }
            }
        }
    }
}