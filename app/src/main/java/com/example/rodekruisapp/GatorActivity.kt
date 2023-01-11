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

class GatorActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup

    //Edit text kan niet leeg zijn
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    lateinit var kenteken: EditText
    private lateinit var emailCc: EditText

    //checkboxes
    private lateinit var checkLaadkabel: CheckBox
    private lateinit var checkKentekenplaten: CheckBox
    private lateinit var checkStryker: CheckBox
    private lateinit var checkStartkabels: CheckBox
    private lateinit var checkBlauwekrat: CheckBox
    private lateinit var checkPapieren: CheckBox
    private lateinit var checkHoofdsteun: CheckBox
    private lateinit var checkKussentje: CheckBox

    private lateinit var min1: Button
    private lateinit var nummer1: TextView
    private lateinit var plus1: Button
    private lateinit var min2: Button
    private lateinit var nummer2: TextView
    private lateinit var plus2: Button

    private lateinit var diversen: EditText

    //huidige tijd
    private lateinit var calendar: Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: String
    private lateinit var datum: TextView
    lateinit var button: Button

    //Voor AVG check
    private lateinit var avg: CheckBox

    //Schadevrij
    private lateinit var schadevrij: CheckBox

    //Verstuur button
    lateinit var verstuur: Button

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gator)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Gator"
        actionBar.setDisplayHomeAsUpEnabled(true)

        radioGroup = findViewById(R.id.radioGroup)

        //Edit text kan niet leeg zijn
        naamGebruiker = findViewById(R.id.naamGebruiker)
        controleur = findViewById(R.id.controleur)
        kenteken = findViewById(R.id.kenteken)
        emailCc = findViewById(R.id.emailCc)

        //checkboxes
        checkLaadkabel = findViewById(R.id.checkLaadkabel)
        checkKentekenplaten = findViewById(R.id.checkKentekenplaten)
        checkStryker = findViewById(R.id.checkStryker)
        checkStartkabels = findViewById(R.id.checkStartkabels)
        checkBlauwekrat = findViewById(R.id.checkBlauwekrat)
        checkPapieren = findViewById(R.id.checkPapieren)
        checkHoofdsteun = findViewById(R.id.checkHoofdsteun)
        checkKussentje = findViewById(R.id.checkKussentje)

        min1 = findViewById(R.id.min1)
        nummer1 = findViewById(R.id.nummer1)
        plus1 = findViewById(R.id.plus1)
        min2 = findViewById(R.id.min2)
        nummer2 = findViewById(R.id.nummer2)
        plus2 = findViewById(R.id.plus2)

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

        var num1 = 0
        plus1.setOnClickListener {
            num1++
            nummer1.text = num1.toString()
        }

        min1.setOnClickListener {
            if(num1 > 0) {
                num1--
            }
            nummer1.text = num1.toString()
        }

        var num2 = 0
        plus2.setOnClickListener {
            num2++
            nummer2.text = num2.toString()
        }

        min2.setOnClickListener {
            if(num2 > 0) {
                num2--
            }
            nummer2.text = num2.toString()
        }


        //Schadevrij
        schadevrij = findViewById(R.id.schadevrij)

        //Verstuur button
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
        intent.putExtra(Intent.EXTRA_SUBJECT, "$a van Gator")
        intent.putExtra(Intent.EXTRA_EMAIL, aEmailList)
        intent.putExtra(Intent.EXTRA_CC, aEmailCC)
        intent.type = "mailto/*"

        val naamGebruiker = naamGebruiker.text.toString()
        val controleur = controleur.text.toString()
        val kenteken = kenteken.text.toString()
        val datum = datum.text.toString()
        val nummer1 = nummer1.text.toString()
        val nummer2 = nummer2.text.toString()
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

        if (checkLaadkabel.isChecked) {
            r += checkLaadkabel.text.toString()
            r = "$r \n"
        }

        if (checkKentekenplaten.isChecked) {
            r += checkKentekenplaten.text.toString()
            r = "$r \n"
        }

        if (checkBlauwekrat.isChecked) {
            r += checkBlauwekrat.text.toString()
            r = "$r \n"
        }
        if (checkStryker.isChecked) {
            r += checkStryker.text.toString()
            r = "$r \n"
        }

        if (checkHoofdsteun.isChecked) {
            r += checkHoofdsteun.text.toString()
            r = "$r \n"
        }

        if (checkKussentje.isChecked) {
            r += checkKussentje.text.toString()
            r = "$r \n"
        }

        if (checkStartkabels.isChecked) {
            r += checkStartkabels.text.toString()
            r = "$r \n"
        }

        if (checkPapieren.isChecked) {
            r += checkPapieren.text.toString()
            r = "$r \n"
        }

        sb.append(r)

        sb.append("Motorhelm: $nummer1 \n")
        sb.append("Motorkleding: $nummer2 \n")

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