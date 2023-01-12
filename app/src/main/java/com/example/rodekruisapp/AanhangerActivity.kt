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

class AanhangerActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup

    //Edit text kan niet leeg zijn
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    lateinit var kenteken: EditText
    private lateinit var emailCc: EditText

    //checkboxes
    private lateinit var checkRijplaten: CheckBox
    private lateinit var checkSjorbanden: CheckBox
    private lateinit var checkKentekenplaten: CheckBox
    private lateinit var checkDisselslot: AutoCompleteTextView

    private val nummers = arrayOf("Disselslot 1","Disselslot 2","Disselslot 3","Disselslot 4")
    private lateinit var arrayAdapter: ArrayAdapter<String>

    private lateinit var diversen: EditText

    //Voor huidige tijd
    private lateinit var calendar: Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: String
    private lateinit var datum: TextView
    lateinit var button: Button

    //Voor schadevrij
    private lateinit var schadevrij: CheckBox

    private lateinit var aanhangerLosMee: CheckBox

    //Voor AVG check
    private lateinit var avg: CheckBox

    //Verstuur Button
    lateinit var verstuur: Button

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aanhanger)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Aanhanger"
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
        checkRijplaten = findViewById(R.id.checkRijplaten)
        checkSjorbanden = findViewById(R.id.checkSjorbanden)
        checkKentekenplaten = findViewById(R.id.checkKentekenplaten)
        checkDisselslot = findViewById(R.id.checkDisselslot)

        //Dropdown voor fietsnummers
        arrayAdapter = ArrayAdapter<String>(this, R.layout.dropdown, nummers)
        checkDisselslot.setAdapter(arrayAdapter)

        diversen = findViewById(R.id.diversen)

        //Var van schadevrij
        schadevrij = findViewById(R.id.schadevrij)

        aanhangerLosMee = findViewById(R.id.aanhangerLosMee)

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
        intent.putExtra(Intent.EXTRA_SUBJECT, "$a van Aanhanger")
        intent.putExtra(Intent.EXTRA_EMAIL, aEmailList)
        intent.putExtra(Intent.EXTRA_CC, aEmailCC)
        intent.type = "mailto/*"

        val naamGebruiker = naamGebruiker.text.toString()
        val controleur = controleur.text.toString()
        val kenteken = kenteken.text.toString()
        val datum = datum.text.toString()
        val checkDisselslot = checkDisselslot.text.toString()
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

        if (checkRijplaten.isChecked) {
            r += checkRijplaten.text.toString()
            r = "$r \n"
        }

        if (checkSjorbanden.isChecked) {
            r += checkSjorbanden.text.toString()
            r = "$r \n"
        }

        if (checkKentekenplaten.isChecked) {
            r += checkKentekenplaten.text.toString()
            r = "$r \n"
        }

        sb.append(r)

        sb.append("$checkDisselslot \n")

        sb.append("Diversen: $diversen \n \n")

        var b = ""
        if (schadevrij.isChecked) {
            b += schadevrij.text.toString()
            b = "$b \n \n"
        }

        if (aanhangerLosMee.isChecked) {
            b += aanhangerLosMee.text.toString()
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