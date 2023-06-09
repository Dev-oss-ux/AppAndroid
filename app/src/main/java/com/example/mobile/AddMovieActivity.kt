package com.example.mobile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import com.example.mobile.db.MovieDatabase

class AddMovieActivity : AppCompatActivity() {
    private lateinit var saveButton: Button
    private lateinit var editTitle: EditText
    private lateinit var editDescription: EditText
    private lateinit var imagePost: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        editTitle = findViewById(R.id.editTitre)
        editDescription = findViewById(R.id.editDescription)
        saveButton = findViewById(R.id.savebtn)
        imagePost = findViewById(R.id.image)

        imagePost.setOnClickListener {
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            intentImg.type = "images/*"
            startActivity(intentImg)
        }

        saveButton.setOnClickListener {
            val title = editTitle.text.toString()
            val description = editDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val imageBitmap: Bitmap? = (imagePost.drawable as? BitmapDrawable)?.bitmap

                if (imageBitmap != null) {
                    val movie = Movie(title, description, imageBitmap)
                    val movieDatabase = MovieDatabase(this)
                    val result = movieDatabase.addMovie(movie)
                    movieDatabase.close()

                    if (result) {
                        Toast.makeText(this, "Film enregistré avec succès", Toast.LENGTH_SHORT).show()
                        println("testmovie" + movieDatabase.getMovies())
                        finish()
                    } else {
                        Toast.makeText(this, "Erreur lors de l'enregistrement du film", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Veuillez sélectionner une image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
