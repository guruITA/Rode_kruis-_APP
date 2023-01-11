//Auto

@file:Suppress("DEPRECATION")

package com.example.rodekruisapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class QuadActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup

    //Edit text kan niet leeg zijn
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    lateinit var kenteken: EditText
    private lateinit var emailCc: EditText

    //checkboxes
    private lateinit var checkQuadtas: CheckBox
    private lateinit var checkNummerplaten: CheckBox
    private lateinit var checkDieselslot: CheckBox
    private lateinit var checkQuadhelm: CheckBox
    private lateinit var checkRijplaten: CheckBox
    private lateinit var checkVoertuigPapieren: CheckBox
    private lateinit var checkQuadaanhanger: CheckBox
    private lateinit var diversen: EditText

    //huidige tijd
    private lateinit var calendar: Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: String
    private lateinit var datum: TextView
    lateinit var button: Button

    private lateinit var schadevrij: CheckBox

    //Voor AVG check
    private lateinit var avg: CheckBox
    lateinit var verstuur: Button

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quad)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Quad"
        actionBar.setDisplayHomeAsUpEnabled(true)

        radioGroup = findViewById(R.id.radioGroup)

        //Edit text kan niet leeg zijn
        naamGebruiker = findViewById(R.id.naamGebruiker)
        controleur = findViewById(R.id.controleur)
        kenteken = findViewById(R.id.kenteken)
        emailCc = findViewById(R.id.emailCc)

        //checkboxes
        checkQuadtas = findViewById(R.id.checkQuadtas)
        checkNummerplaten = findViewById(R.id.checkNummerplaten)
        checkDieselslot = findViewById(R.id.checkDieselslot)
        checkQuadhelm = findViewById(R.id.checkQuadhelm)
        checkRijplaten = findViewById(R.id.checkRijplaten)
        checkVoertuigPapieren = findViewById(R.id.checkVoertuigPapieren)
        checkQuadaanhanger = findViewById(R.id.checkQuadaanhanger)
        diversen = findViewById(R.id.diversen)

        //huidige tijd
        datum = findViewById(R.id.datum)
        button = findViewById(R.id.btndatum)
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        date = simpleDateFormat.format(calendar.time)
        button.setOnClickListener { datum.text = date }

        //Voor AVG check
        avg = findViewById(R.id.AVG)

        //Var van schadevrij
        schadevrij = findViewById(R.id.schadevrij)

        verstuur = findViewById(R.id.verstuur)
        val builder = AlertDialog.Builder(this)

        verstuur.setOnClickListener {

            if (naamGebruiker.text.toString().trim().isEmpty() ||
                controleur.text.toString().trim().isEmpty() ||
                kenteken.text.toString().trim().isEmpty() ||
                datum.text.toString().trim().isEmpty() ||
                !avg.isChecked
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

                if (!avg.isChecked) {
                    avg.error = "Verplicht"
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

        val aEmailList = arrayOf("Mdlogistiek@redcross.nl")
        val emailCc = emailCc.text.toString()
        val aEmailCC = arrayOf(emailCc)

        //Om bij de subject te laten zien of het een inname is of een uitgifte
        val selectRadio: Int = radioGroup.checkedRadioButtonId
        val radioButtons = findViewById<RadioButton>(selectRadio)
        var a = ""
        if (radioButtons.isChecked) {
            a += radioButtons.text.toString()
        }

        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.putExtra(Intent.EXTRA_SUBJECT, "$a van Quad")
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
            "Datum en tijd: $datum"
        )

        val sb = StringBuilder()
        sb.append("Gegevens: \n")
        for (b in veld) {
            sb.append(b)
            sb.append("\n")
        }

        sb.append("\n")

        sb.append("Accessories: \n")

        var r = ""

        if (checkQuadtas.isChecked) {
            r += checkQuadtas.text.toString()
            r = "$r \n"
        }

        if (checkNummerplaten.isChecked) {
            r += checkNummerplaten.text.toString()
            r = "$r \n"
        }

        if (checkQuadhelm.isChecked) {
            r += checkQuadhelm.text.toString()
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

        if (checkVoertuigPapieren.isChecked) {
            r += checkVoertuigPapieren.text.toString()
            r = "$r \n"
        }

        if (checkQuadaanhanger.isChecked) {
            r += checkQuadaanhanger.text.toString()
            r = "$r \n"
        }


        sb.append(r)

        sb.append("Diversen: $diversen \n \n")

        var b = ""
        if (schadevrij.isChecked) {
            b += schadevrij.text.toString()
            b = "$b \n \n"
        }

        sb.append(b)

        sb.append("Toestemming: \n")

        var x = ""
        if (avg.isChecked) {
            x += avg.text.toString()
            x = "$x \n"
        }

        sb.append(x)

        intent.putExtra(Intent.EXTRA_TEXT, sb.toString())
        startActivity(Intent.createChooser(intent, "Kies een e-mailclient"))

    }
}