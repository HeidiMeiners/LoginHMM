package com.example.peliculashmm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import com.google.firebase.database.ValueEventListener

class Home : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    val myRef = Firebase.database.getReference("peliculas")

    lateinit var peliculas: ArrayList<Peliculas>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        val extras = intent.extras

        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                peliculas= ArrayList<Peliculas>()

                val value = snapshot.value
                println(value)
                snapshot.children.forEach { unit ->
                    var pelicula = Peliculas(unit.child("nombre").value.toString(),unit.child("genero").value.toString(),unit.child("anio").value.toString(),unit.key.toString())
                    peliculas.add(pelicula)
                }
                llenaLista()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("real-time-database", "Failed to read value.", error.toException())
            }

        })

        val lista= findViewById<ListView>(R.id.lista)
        lista.setOnItemClickListener{parent,view,position,id ->
            startActivity(Intent(this, Detalle::class.java)
                .putExtra("id",peliculas[position].id)
                .putExtra("Nombre", peliculas[position].nombre)
                .putExtra("Genero", peliculas[position].genero)
                .putExtra("Anio", peliculas[position].anio))
        }

        val agregar=findViewById<FloatingActionButton>(R.id.agregarHome)
        agregar.setOnClickListener {
            startActivity(Intent(this, Agregar::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun llenaLista(){
        val adaptador= PeliAdapter(this,peliculas)
        val lista = findViewById<ListView>(R.id.lista)
        lista.adapter=adaptador
    }
}