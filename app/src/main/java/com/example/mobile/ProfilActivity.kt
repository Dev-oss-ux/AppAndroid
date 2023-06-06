package com.example.mobile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ProfilActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var newPasswordEditText: EditText
    private lateinit var saveButton: Button

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)

        val textView2: TextView = findViewById(R.id.textView2)
        val textView3: TextView = findViewById(R.id.textView3)
        val textView4: TextView = findViewById(R.id.textView4)
        saveButton = findViewById(R.id.saveButton)

        imageView = findViewById(R.id.imageView3)
        imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        saveButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            saveNewPassword(newPassword)
        }

        val intent = intent
        val email = intent.getStringExtra("email")
        val nom = intent.getStringExtra("nom")
        val prenom = intent.getStringExtra("prenom")
        val age = intent.getStringExtra("age")

        textView2.text = email
        textView3.text = nom
        textView4.text = prenom
        textView4.text = age
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            // Utilisez l'URI de l'image sélectionnée pour l'afficher dans l'imageView
            imageView.setImageURI(imageUri)
        }
    }

    private fun saveNewPassword(newPassword: String) {
        // Code pour sauvegarder le nouveau mot de passe
        // Vous pouvez implémenter ici la logique nécessaire pour enregistrer le nouveau mot de passe dans votre système.
        // Par exemple, vous pouvez utiliser une base de données ou une API pour mettre à jour le mot de passe de l'utilisateur.
        Toast.makeText(this, "Mot de passe modifié avec succès", Toast.LENGTH_SHORT).show()
    }
}
