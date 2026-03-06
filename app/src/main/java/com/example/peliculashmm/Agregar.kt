package com.example.peliculashmm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database

class Agregar : AppCompatActivity() {
    private val myRef= Firebase.database.getReference("peliculas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar)

        val agregarPeli = findViewById<Button>(R.id.agregar)

        agregarPeli.setOnClickListener {
            val Nombre= findViewById<EditText>(R.id.agregarNombre).text.toString()
            val Anio= findViewById<EditText>(R.id.agregarAnio).text.toString()
            val Genero= findViewById<EditText>(R.id.agregarGenero).text.toString()

            if(Nombre.isEmpty() || Anio.isEmpty() || Genero.isEmpty()){
                Toast.makeText(this,"Faltan datos", Toast.LENGTH_SHORT).show()
            }
            else{
                val pelicula: PeliculaSinId = PeliculaSinId(Nombre,Anio,Genero)
                myRef.push().setValue(pelicula)
                Toast.makeText(this,"Pelicula agregada", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Home::class.java))
                finish()
            }
        }

    }
}