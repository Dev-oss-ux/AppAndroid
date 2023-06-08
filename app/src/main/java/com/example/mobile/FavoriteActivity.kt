package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile.data.Post

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val post = intent.getSerializableExtra("film") as? Post
        val tvFilmTitle = findViewById<TextView>(R.id.tvFilmTitle)
        val tvFilmNote = findViewById<TextView>(R.id.tvFilmNote)
        val tvFilmBudget = findViewById<TextView>(R.id.tvFilmBudget)
        val tvFilmPublication = findViewById<TextView>(R.id.tvFilmPublication)
        val tvFilmDescription = findViewById<TextView>(R.id.tvFilmDescription)

        tvFilmTitle.text = post?.titre
        tvFilmNote.text = post?.note
        tvFilmBudget.text = post?.budget
        tvFilmPublication.text = post?.publication
        tvFilmDescription.text = post?.description
    }
}


