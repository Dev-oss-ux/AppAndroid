package com.example.mobile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mobile.db.MovieDatabase

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var db: MovieDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val email = findViewById<EditText>(R.id.editTextTextPersonName3)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val connect = findViewById<Button>(R.id.connect)

        // État de l'application : connectée ou pas :
        sharedPreferences = this.getSharedPreferences("app_state", Context.MODE_PRIVATE)
        db = MovieDatabase(this) // Initialisation de la base de données

        val isAuthenticated = sharedPreferences.getBoolean("is_authenticated", false)
        val emailSharedPreferences = sharedPreferences.getString("email", null)
        if (isAuthenticated) {
            Intent(this, HomeActivity::class.java).also {
                it.putExtra("email", emailSharedPreferences)
                startActivity(it)
                 // Terminer l'activité MainActivity pour empêcher le retour en arrière
            }
        }

        connect.setOnClickListener {
            val txtEmail = email.text.toString()
            val txtPassword = password.text.toString()
            if (txtEmail.trim().isEmpty() || txtPassword.trim().isEmpty()) {
                Toast.makeText(this, "Vous devez remplir tous les champs", Toast.LENGTH_LONG).show()
            } else {
                val user = db.getUserByEmail(txtEmail)
                if (user != null && user.password == txtPassword) {
                    email.setText("")
                    password.setText("")

                   val editor = sharedPreferences.edit()
                   editor.putBoolean("is_authenticated", true)
                   editor.putString("email", txtEmail)
                    editor.apply()

                    val intentToHomeActivity = Intent(this, HomeActivity::class.java).apply {
                        putExtra("email", txtEmail)
                    }
                    startActivity(intentToHomeActivity)
                    finish() // Terminer l'activité MainActivity pour empêcher le retour en arrière
                } else {
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }

        tvRegister.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
                finish() // Terminer l'activité MainActivity pour empêcher le retour en arrière
            }
        }
    }
}
