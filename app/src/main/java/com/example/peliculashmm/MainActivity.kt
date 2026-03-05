package com.example.peliculashmm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var emailEditable: EditText
    lateinit var passwordEditable: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        emailEditable = findViewById<EditText>(R.id.emailEditable)
        passwordEditable = findViewById<EditText>(R.id.passwordEditable)
    }

    //"heidi@heidi.com","heidi123"
    fun login(view: View){
        val email = emailEditable.text.toString().trim()
        val password = passwordEditable.text.toString().trim()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,"login exitoso",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,Home::class.java).putExtra("email",task.result.user?.email.toString()))
            }
            else{
                Toast.makeText(this,task.exception?.message.toString(),Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onStart(){
        super.onStart()
        val usuarioActual = Firebase.auth.currentUser

        if(usuarioActual!=null){
            startActivity(Intent(this,Home::class.java))
            Toast.makeText(this,"Usuario previemante autenticado",Toast.LENGTH_LONG).show()
            finish()
        }
    }
}