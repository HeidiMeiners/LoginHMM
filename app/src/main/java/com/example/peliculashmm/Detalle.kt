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

class Detalle : AppCompatActivity() {
    private lateinit var id: String
    private val myRef= Firebase.database.getReference("peliculas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)

        id = intent.getStringExtra("id").toString()

        val nombre = intent.getStringExtra("Nombre")
        val genero = intent.getStringExtra("Genero")
        val anio = intent.getStringExtra("Anio")

        findViewById<EditText>(R.id.nombre).setText(nombre)
        findViewById<EditText>(R.id.genero).setText(genero)
        findViewById<EditText>(R.id.anio).setText(anio)

        findViewById<Button>(R.id.guardar).setOnClickListener {

            val nuevoNombre = findViewById<EditText>(R.id.nombre).text.toString()
            val nuevoGenero = findViewById<EditText>(R.id.genero).text.toString()
            val nuevoAnio = findViewById<EditText>(R.id.anio).text.toString()

            val actualizacion = HashMap<String, Any>()
            actualizacion["nombre"] = nuevoNombre
            actualizacion["genero"] = nuevoGenero
            actualizacion["anio"] = nuevoAnio

            myRef.child(id).updateChildren(actualizacion)
                .addOnSuccessListener {
                    Toast.makeText(this, "Película actualizada", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
        }

        val eliminar= findViewById<Button>(R.id.eliminar)
        eliminar.setOnClickListener {
            myRef.child(id).removeValue()
            Toast.makeText(this,"Pelicula eliminada", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Home::class.java))
            finish()
        }

    }

}