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

class FietsActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup

    //Edittext
    lateinit var naamGebruiker: EditText
    lateinit var controleur: EditText
    private lateinit var fietsnummer: AutoCompleteTextView
    private lateinit var emailCc: EditText

    //Checkboxes
    private lateinit var checkFietsaanhanger: CheckBox
    private lateinit var checkEhbotas: CheckBox
    private lateinit var checkVoorlampje: CheckBox
    private lateinit var checkVlaggetje: CheckBox
    private lateinit var min1: Button
    private lateinit var nummer1: TextView
    private lateinit var plus1: Button

    private lateinit var diversen: EditText

    //Voor huidige tijd
    private lateinit var calendar: Calendar
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var date: String
    private lateinit var datum: TextView
    lateinit var button: Button

    //Voor chadevrij
    private lateinit var schadevrij: CheckBox

    //Voor koppeling van de fietsaanhanger
    lateinit var checkKoppelingAanhanger: CheckBox

    //Voor AVG check
    private lateinit var avg: CheckBox

    //Verstuur Button
    lateinit var verstuur: Button

    //Var voor fietsnummers
    private val nummers = arrayOf(
        "Fietsnummer 1",
        "Fietsnummer 2",
        "Fietsnummer 3",
        "Fietsnummer 4",
        "Fietsnummer 5",
        "Fietsnummer 6"
    )
    private lateinit var arrayAdapter: ArrayAdapter<String>


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fiets)

        //naam en terug naar main pagina
        val actionBar = supportActionBar
        actionBar!!.title = "Fiets"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //Var voor radiogroep inname en uitgifte
        radioGroup = findViewById(R.id.radioGroup)

        //Var van edittexten
        naamGebruiker = findViewById(R.id.naamGebruiker)
        controleur = findViewById(R.id.controleur)
        fietsnummer = findViewById(R.id.fietsnummer)
        emailCc = findViewById(R.id.emailCc)

        //Dropdown voor fietsnummers
        arrayAdapter = ArrayAdapter<String>(this, R.layout.dropdown, nummers)
        fietsnummer.setAdapter(arrayAdapter)


        //Var voor huidige tijd
        datum = findViewById(R.id.datum)
        button = findViewById(R.id.btndatum)

        //Zorgt ervoor dat als je de button klikt je de huidige datum en tijd te zien krijgt
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        date = simpleDateFormat.format(calendar.time)
        button.setOnClickListener { datum.text = date }

        //Var van checkboxes en edittext "diversen"
        checkFietsaanhanger = findViewById(R.id.checkFietsaanhanger)
        checkVlaggetje = findViewById(R.id.checkVlaggetje)
        checkEhbotas = findViewById(R.id.checkEhbotas)
        checkVoorlampje = findViewById(R.id.checkVoorlampje)
        min1 = findViewById(R.id.min1)
        nummer1 = findViewById(R.id.nummer1)
        plus1 = findViewById(R.id.plus1)
        diversen = findViewById(R.id.diversen)

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

        //Var van schadevrij
        schadevrij = findViewById(R.id.schadevrij)

        //koppeling aanhanger
        checkKoppelingAanhanger = findViewById(R.id.checkKoppelingAanhanger)

        //Var van checkbox AVG
        avg = findViewById(R.id.AVG)

        //Var van verstuur button
        verstuur = findViewById(R.id.verstuur)
        val builder = AlertDialog.Builder(this)

        //Als je verstuur klikt controleerd of naamGebruiker, controleur, fietsnummer, datum zijn ingevuld en dat ze de AVG checkbox hebben aangevinkt
        verstuur.setOnClickListener {

            if (naamGebruiker.text.toString().trim().isEmpty() ||
                controleur.text.toString().trim().isEmpty() ||
                fietsnummer.text.toString().trim().isEmpty() ||
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
                if (fietsnummer.text.toString().trim().isEmpty()) {
                    fietsnummer.error = "Verplicht"
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
        intent.putExtra(Intent.EXTRA_SUBJECT, "$a van Fiets")
        intent.putExtra(Intent.EXTRA_EMAIL, aEmailList)
        intent.putExtra(Intent.EXTRA_CC, aEmailCC)
        intent.type = "mailto/*"

        val naamGebruiker = naamGebruiker.text.toString()
        val controleur = controleur.text.toString()
        val fietsnummer = fietsnummer.text.toString()
        val datum = datum.text.toString()
        val nummer1 = nummer1.text.toString()
        val diversen = diversen.text.toString()

        val veld = arrayListOf(
            "Naam: $naamGebruiker",
            "Controleur: $controleur",
            "Kenteken: $fietsnummer",
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

        if (checkFietsaanhanger.isChecked) {
            r += checkFietsaanhanger.text.toString()
            r = "$r \n"
        }

        if (checkVlaggetje.isChecked) {
            r += checkVlaggetje.text.toString()
            r = "$r \n"
        }

        if (checkEhbotas.isChecked) {
            r += checkEhbotas.text.toString()
            r = "$r \n"
        }

        if (checkVoorlampje.isChecked) {
            r += checkVoorlampje.text.toString()
            r = "$r \n"
        }

        sb.append(r)

        sb.append("Fietshelm: $nummer1 \n")

        sb.append("Diversen: $diversen \n \n")

        var b = ""
        if (schadevrij.isChecked) {
            b += schadevrij.text.toString()
            b = "$b \n \n"
        }

        if (checkKoppelingAanhanger.isChecked) {
            b += checkKoppelingAanhanger.text.toString()
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