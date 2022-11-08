//Auto

@file:Suppress("DEPRECATION")

package com.example.rodekruisapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import java.text.SimpleDateFormat
import java.util.*

class FirstActivity : AppCompatActivity() {

    //Edit text kan niet leeg zijn
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    lateinit var kenteken: EditText
    lateinit var email: EditText

    //checkboxes
    lateinit var checkKabel: CheckBox
    lateinit var checkNummerplaten: CheckBox
    lateinit var checkDieselslot: CheckBox
    lateinit var checkFietsendrager: CheckBox
    lateinit var checkRijplaten: CheckBox

    //huidige tijd
    private lateinit var calendar: Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: String
    private lateinit var datum: TextView
    lateinit var button: Button

    //foto uploaden
    private lateinit var pickIImageSwitcher: ImageSwitcher
    private lateinit var previosBtn: Button
    lateinit var nextBtn: Button
    lateinit var pickImageBtn: Button

    private var images: ArrayList<Uri?>? = null
    private var position = 0
    private val pickimagescode = 0

    //Voor AVG check
    lateinit var verstuur: Button

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Auto"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Edit text kan niet leeg zijn
        email = findViewById(R.id.email)
        naamGebruiker = findViewById(R.id.naamGebruiker)
        controleur = findViewById(R.id.controleur)
        kenteken = findViewById(R.id.kenteken)

        //checkboxes
        checkKabel = findViewById(R.id.checkKabel)
        checkNummerplaten = findViewById(R.id.checkNummerplaten)
        checkDieselslot = findViewById(R.id.checkDieselslot)
        checkFietsendrager = findViewById(R.id.checkFietsendrager)
        checkRijplaten = findViewById(R.id.checkRijplaten)

        //huidige tijd
        datum = findViewById(R.id.datum)
        button = findViewById(R.id.btndatum)
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
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
        val builder = AlertDialog.Builder(this)

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
                    //sendMail()
                    .setPositiveButton("Ja") { dialogInterface, to -> sendMail().to(finish()) }
                    .setNegativeButton("Nee") { dialogInterface, to -> dialogInterface.cancel() }
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
        startActivityForResult(Intent.createChooser(intent, "Select image(s)"), pickimagescode)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickimagescode) {

            if (resultCode == Activity.RESULT_OK) {

                if (data!!.clipData != null) {
                    val count = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        images!!.add(imageUri)
                    }

                    pickIImageSwitcher.setImageURI(images!![0])
                    position = 0

                    //Dit zijn om de vorige en volgende foto's te bekijken. Ze zijn zichtbaar als meer dan een foto gaat selecteren
                    if (previosBtn.isGone || nextBtn.isGone) {
                        previosBtn.visibility = View.VISIBLE
                        nextBtn.visibility = View.VISIBLE
                    } else {
                        previosBtn.visibility = View.GONE
                        nextBtn.visibility = View.GONE
                    }

                } else {
                    val imageUri = data.data
                    pickIImageSwitcher.setImageURI(imageUri)
                    position = 0
                }
            }
        }
    }

    //Gegevens sturen naar email
    private fun sendMail() {

        val recipientList = email!!.text.toString()
        val recipients = recipientList.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        val naamGebruiker = naamGebruiker.text.toString()
        val controleur = controleur.text.toString()
        val kenteken = kenteken.text.toString()
        val datum = datum.text.toString()
        val checkKabel = checkKabel.text.toString()
        val checkNummerplaten = checkNummerplaten.text.toString()
        val checkDieselslot = checkDieselslot.text.toString()
        val checkFietsendrager = checkFietsendrager.text.toString()
        val checkRijplaten = checkRijplaten.text.toString()

        val veld = arrayListOf("Naam: $naamGebruiker", "Controleur: $controleur", "Kenteken: $kenteken", "Datum: $datum", checkKabel)

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Uitgifte auto")
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);

        val sb = StringBuilder()
        for (a in veld) {
            sb.append(a)
            sb.append("\n")
        }

        intent.putExtra(Intent.EXTRA_TEXT, sb.toString())

        intent.type = "mailto/rfc822"
        startActivity(Intent.createChooser(intent, "Choose an email client"))

    }
}