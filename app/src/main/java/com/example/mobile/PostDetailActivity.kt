package com.example.mobile

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val tvTitre = findViewById<TextView>(R.id.tvTitre)
        val tvNote = findViewById<TextView>(R.id.tvNote)
        val tvBudget = findViewById<TextView>(R.id.tvBudget)
        val tvDescription = findViewById<TextView>(R.id.tvDescription1)
        val tvPublication = findViewById<TextView>(R.id.tvPublication)
        val tvImageView = findViewById<ImageView>(R.id.imageView)



        val titre = intent.getStringExtra("titre")
        val note = intent.getStringExtra("note")
        val budget = intent.getStringExtra("budget")
        val publication = intent.getStringExtra("publication")
        val description = intent.getStringExtra("description")


        tvTitre.text = titre
        tvDescription.text = description
        tvBudget.text = budget
        tvPublication.text = publication
        tvNote.text = note


    }
}