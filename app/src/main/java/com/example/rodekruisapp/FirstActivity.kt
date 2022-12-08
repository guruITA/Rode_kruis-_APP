//Auto

@file:Suppress("DEPRECATION")

package com.example.rodekruisapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class FirstActivity : AppCompatActivity() {

    //Edit text kan niet leeg zijn
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    lateinit var kenteken: EditText
    private lateinit var emailCc: EditText

    //checkboxes
    private lateinit var checkKabel: CheckBox
    private lateinit var checkNummerplaten: CheckBox
    private lateinit var checkDieselslot: CheckBox
    private lateinit var checkFietsendrager: CheckBox
    private lateinit var checkRijplaten: CheckBox
    private lateinit var checkAutoPapieren: CheckBox
    lateinit var diversen: EditText

    //huidige tijd
    private lateinit var calendar: Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: String
    private lateinit var datum: TextView
    lateinit var button: Button

    //Voor AVG check
    private lateinit var AVG: CheckBox
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
        emailCc = findViewById(R.id.emailCc)
        naamGebruiker = findViewById(R.id.naamGebruiker)
        controleur = findViewById(R.id.controleur)
        kenteken = findViewById(R.id.kenteken)

        //checkboxes
        checkKabel = findViewById(R.id.checkKabel)
        checkNummerplaten = findViewById(R.id.checkNummerplaten)
        checkDieselslot = findViewById(R.id.checkDieselslot)
        checkFietsendrager = findViewById(R.id.checkFietsendrager)
        checkRijplaten = findViewById(R.id.checkRijplaten)
        checkAutoPapieren = findViewById(R.id.checkAutoPapieren)
        diversen = findViewById(R.id.diversen)

        //huidige tijd
        datum = findViewById(R.id.datum)
        button = findViewById(R.id.btndatum)
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        date = simpleDateFormat.format(calendar.time)
        button.setOnClickListener { datum.text = date }

        //Voor AVG check
        AVG = findViewById(R.id.AVG)
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

                if (!AVG.isChecked){
                    AVG.error = "Verplicht"
                }

            } else {

                //Voor AVG check
                builder.setTitle("AVG toestemming verwerken persoongegevens")
                    .setMessage("Uw gegevens worden 3 maanden in ons systeem bewaard, gaat u hiermee akkoord?")
                    .setCancelable(true)
                    .setPositiveButton("Akkord") { dialogInterface, to -> sendMail().to(finish()) }
                    .setNegativeButton("Niet akkoord") { dialogInterface, to -> dialogInterface.cancel() }
                    .show()

            }
        }
    }

    //Gegevens sturen naar email
    private fun sendMail() {

        val aEmailList = arrayOf("2087628@talnet.nl")
        val emailCc = emailCc.text.toString()
        val aEmailCC = arrayOf(emailCc)

        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Uitgifte auto")
        intent.putExtra(Intent.EXTRA_EMAIL, aEmailList)
        intent.putExtra(Intent.EXTRA_CC, aEmailCC)
        intent.type = "mailto/*"

        val naamGebruiker = naamGebruiker.text.toString()
        val controleur = controleur.text.toString()
        val kenteken = kenteken.text.toString()
        val datum = datum.text.toString()
        val diversen = diversen.text.toString()

        val veld = arrayListOf(
            "Naam: $naamGebruiker",
            "Controleur: $controleur",
            "Kenteken: $kenteken",
            "Datum: $datum"
        )

        val sb = StringBuilder()
        sb.append("Gegevens:", "\n")
        for (a in veld) {
            sb.append(a)
            sb.append("\n")
        }

        sb.append("\n")
        sb.append("Meegenomen accessories:", "\n")

        var r = ""

        if (checkKabel.isChecked) {
            r += checkKabel.text.toString()
            r = "$r \n"
        }
3

        if (checkNummerplaten.isChecked) {
            r += checkNummerplaten.text.toString()
            r = "$r \n"
        }

        if (checkFietsendrager.isChecked) {
            r += checkFietsendrager.text.toString()
            r = "$r \n"
        }

        if (checkDieselslot.isChecked) {
            r += checkDieselslot.text.toString()
            r = "$r \n"
        }

        if (checkRijplaten.isChecked) {
            r += checkRijplaten.text.toString()
            r = "$r \n"
        }
        if (checkAutoPapieren.isChecked) {
            r += checkAutoPapieren.text.toString()
            r = "$r \n"
        }

        sb.append("Diversen: $diversen")

        intent.putExtra(Intent.EXTRA_TEXT, sb.toString())
        startActivity(Intent.createChooser(intent, "Kies een e-mailclient"))

    }
}