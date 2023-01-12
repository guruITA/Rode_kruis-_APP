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

class AutoActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup

    //Edit text kan niet leeg zijn
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    lateinit var kenteken: EditText
    private lateinit var emailCc: EditText

    //checkboxes
    private lateinit var checkLaadkabel: CheckBox
    private lateinit var checkKentekenplaten: CheckBox
    private lateinit var checkTelefoon: CheckBox
    private lateinit var checkFietsendrager: CheckBox
    private lateinit var checkBuitenDienstBordje: CheckBox
    private lateinit var checkNavigatie: CheckBox
    private lateinit var checkPapieren: CheckBox
    private lateinit var diversen: EditText

    //Voor huidige tijd
    private lateinit var calendar: Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: String
    private lateinit var datum: TextView
    lateinit var button: Button

    //Voor chadevrij
    private lateinit var schadevrij: CheckBox

    //Voor AVG check
    private lateinit var avg: CheckBox

    //Verstuur Button
    lateinit var verstuur: Button

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Auto"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Var voor radiogroep inname en uitgifte
        radioGroup = findViewById(R.id.radioGroup)

        //Var van edittexten
        naamGebruiker = findViewById(R.id.naamGebruiker)
        controleur = findViewById(R.id.controleur)
        kenteken = findViewById(R.id.kenteken)
        emailCc = findViewById(R.id.emailCc)

        //Var voor huidige tijd
        datum = findViewById(R.id.datum)
        button = findViewById(R.id.btndatum)

        //Zorgt ervoor dat als je de button klikt je de huidige datum en tijd te zien krijgt
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        date = simpleDateFormat.format(calendar.time)
        button.setOnClickListener { datum.text = date }

        //Var van checkboxes en edittext "diversen"
        checkLaadkabel = findViewById(R.id.checkLaadkabel)
        checkKentekenplaten = findViewById(R.id.checkKentekenplaten)
        checkTelefoon = findViewById(R.id.checkTelefoon)
        checkFietsendrager = findViewById(R.id.checkFietsendrager)
        checkBuitenDienstBordje = findViewById(R.id.checkBuitenDienstBordje)
        checkNavigatie = findViewById(R.id.checkNavigatie)
        checkPapieren = findViewById(R.id.checkPapieren)
        diversen = findViewById(R.id.diversen)

        //Var van schadevrij
        schadevrij = findViewById(R.id.schadevrij)

        //Var van checkbox AVG
        avg = findViewById(R.id.AVG)

        //Var van verstuur button
        verstuur = findViewById(R.id.verstuur)
        val builder = AlertDialog.Builder(this)

        //Als je verstuur klikt controleerd of naamGebruiker, controleur, fietsnummer, datum zijn ingevuld en dat ze de AVG checkbox hebben aangevinkt
        verstuur.setOnClickListener {

            if (naamGebruiker.text.toString().trim().isEmpty() ||
                controleur.text.toString().trim().isEmpty() ||
                kenteken.text.toString().trim().isEmpty() ||
                datum.text.toString().trim().isEmpty() ||
                !avg.isChecked
            ) {

                //Hier wordt gecontroleerd als de velden zijn ingevuld
                if (naamGebruiker.text.toString().trim().isEmpty()) {
                    naamGebruiker.error = "Verplicht"
                }
                if (controleur.text.toString().trim().isEmpty()) {
                    controleur.error = "Verplicht"
                }
                if (kenteken.text.toString().trim().isEmpty()) {
                    kenteken.error = "Verplicht"
                }

                //Hier wordt gecontroleerd of de checkbox is aangevinkt
                if (!avg.isChecked) {
                    avg.error = "Verplicht"
                }

            } else {

                //Nadat je alles hebt gedaan wat verplicht is krijg je een pop-up waar je akkoord moet klikken anders kan je geen email sturen
                builder.setTitle("AVG toestemming verwerken persoongegevens")
                    .setMessage("Uw gegevens worden 3 maanden in ons systeem bewaard, gaat u hiermee akkoord?")
                    .setCancelable(true)
                    .setPositiveButton("Akkoord") { dialogInterface, to -> sendMail().to(finish()) }
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
        intent.putExtra(Intent.EXTRA_SUBJECT, "$a van Auto")
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

        if (checkLaadkabel.isChecked) {
            r += checkLaadkabel.text.toString()
            r = "$r \n"
        }

        if (checkKentekenplaten.isChecked) {
            r += checkKentekenplaten.text.toString()
            r = "$r \n"
        }

        if (checkFietsendrager.isChecked) {
            r += checkFietsendrager.text.toString()
            r = "$r \n"
        }

        if (checkTelefoon.isChecked) {
            r += checkTelefoon.text.toString()
            r = "$r \n"
        }

        if (checkBuitenDienstBordje.isChecked) {
            r += checkBuitenDienstBordje.text.toString()
            r = "$r \n"
        }

        if (checkNavigatie.isChecked) {
            r += checkNavigatie.text.toString()
            r = "$r \n"
        }

        if (checkPapieren.isChecked) {
            r += checkPapieren.text.toString()
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